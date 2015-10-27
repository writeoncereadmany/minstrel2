package com.writeoncereadmany.minstrel.harness;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import com.writeoncereadmany.minstrel.compile.types.validators.InterfaceRule;
import com.writeoncereadmany.minstrel.compile.visitors.DefineNames;
import com.writeoncereadmany.minstrel.compile.visitors.ResolveNames;
import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.visitors.TypeChecker;
import com.writeoncereadmany.minstrel.harness.utils.TestErrorListener;
import com.writeoncereadmany.minstrel.orchestrator.MinstrelOrchestrator;
import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.writeoncereadmany.minstrel.harness.utils.FileUtils.firstLine;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * Test harness for running Minstrel scripts and ensuring the results are as expected: either compilation fails at the
 * expected point, or we get the expected output.
 *
 * This iterates recursively over <name>.minstrel files in test/samples, and runs them through the test runner which applies
 * the following steps in order:
 * 1) Parsing - if this fails, then we verify there is a corresponding <name>.parseError file at the same level as the failed script
 * which matches the parse failure encountered
 * 2) Name resolution - if this fails, then we verify there is a corresponding <name>.nameResolutionError file
 * 3) Type checking - if this fails, then we verify there is a corresponding <name>.typeError file
 * 4) Runs the file - if we encounter a runtime error, then we verify there is a corresponding <name>.runtimeError file,
 * otherwise we verify there's a corresponding <name>.out file (empty if we don't print anything)
 *
 */
public class SampleProgramRunner
{
    public static final File ROOT_SCRIPT_DIR = new File("test/samples");


    @Test
    public void allScriptsHaveExpectedResults() throws Exception
    {
        final List<String> errorCollector = new ArrayList<>();
        testScriptsIn(ROOT_SCRIPT_DIR, errorCollector);
        assertThat(errorCollector, is(empty()));
    }

    @Test
    public void testASingleScript() throws Exception
    {
        final List<String> errorCollector = new ArrayList<>();
        runFileAndVerifyResults(new File(ROOT_SCRIPT_DIR, "implemented/typechecking/valid_call.minstrel"), errorCollector);
        assertThat(errorCollector, is(empty()));
    }

    private void testScriptsIn(File dir, List<String> errorCollector)
    {
        final File[] children = dir.listFiles();

        if(children == null)
        {
            throw new IllegalArgumentException("Should only pass directories into this method");
        }

        stream(children).forEach(file -> {
            if (file.isDirectory()) {
                testScriptsIn(file, errorCollector);
            } else if(file.getName().endsWith(".minstrel")) {
                runFileAndVerifyResults(file, errorCollector);
            }
        });
    }

    private void runFileAndVerifyResults(File file, List<String> errorCollector)
    {
        try {
            final TestErrorListener lexErrorListener = new TestErrorListener();
            final TestErrorListener parseErrorListener = new TestErrorListener();
            MinstrelOrchestrator orchestrator = new MinstrelOrchestrator(lexErrorListener, parseErrorListener);
            TokenStream lexed = orchestrator.lex(new FileInputStream(file));
            ParseTree parseTree = orchestrator.parse(lexed);

            if (hasExpectedLexErrors(file, errorCollector, lexErrorListener))
            {
                return;
            }

            if (hasExpectedParseErrors(file, errorCollector, parseErrorListener))
            {
                return;
            }

            // turn parse tree into program, then run it
            Program program = orchestrator.build(parseTree);

            NameResolver nameResolver = new NameResolver();
            Builtins.defineBuiltins(nameResolver);

            Map<ScopeIndex, Typed> types = new HashMap<>();
            Builtins.defineTypesOfPreludeValues(types);
            program.visit(new DefineNames(nameResolver, types));
            program.visit(new ResolveNames(nameResolver, types));

            if(hasExpectedNameErrors(file, errorCollector, nameResolver))
            {
                return;
            }

            Map<ScopeIndex, Type> typeDefinitions = new HashMap<>();

            Builtins.definePreludeTypes(typeDefinitions);

            TypeEngine typeEngine = new TypeEngine(asList(new FunctionRules(), new InterfaceRule(), new ImplementationRule()),
                                                   typeDefinitions);

            TypeChecker typeChecker = new TypeChecker(typeEngine);
            program.visit(typeChecker);

            List<TypeError> errors = typeChecker.getTypeErrors();

            ByteArrayOutputStream printed = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(printed);
            try
            {
                Interpreter interpreter = new Interpreter(nameResolver, Builtins.getPrelude(printStream));
                program.visit(interpreter);
            }
            catch (RuntimeException ex)
            {
                if (hasExpectedRuntimeErrors(file, errorCollector, ex))
                {
                    return;
                }
            }

            verifyOutput(file, printed, errorCollector);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Failed to load file", ex);
        }
        catch (RuntimeException ex)
        {
            throw new RuntimeException("Error when parsing " + file.getName(), ex);
        }
    }

    private void verifyOutput(File file, ByteArrayOutputStream printed, List<String> errorCollector) throws IOException
    {
        String actualOutputString = printed.toString();

        Path expectedOutputPath = replaceExtension(file, "out").toPath();
        Collector<? super String, StringBuilder, String> joiner = joinWith("\n");

        if(actualOutputString.isEmpty() && !Files.exists(expectedOutputPath))
        {
            // everything's fine! no need to do anything. but it's clearer with all possibilities enumerated
        }
        else if(actualOutputString.isEmpty() && Files.exists(expectedOutputPath))
        {
            errorCollector.add("Expected output from " + file.getName() + " but got none");
        }
        else if(!actualOutputString.isEmpty() && !Files.exists(expectedOutputPath))
        {
            errorCollector.add("Expected no output from " + file.getName() + " but got: " + actualOutputString);
        }
        else
        {
            List<String> actualOutput = asList(actualOutputString.split("\n"));
            List<String> expectedOutput = Files.lines(expectedOutputPath).collect(Collectors.toList());

            if(!actualOutput.equals(expectedOutput))
            {
                errorCollector.add("Wrong output from " + file.getName() + ". Expected:\n" +
                                   expectedOutput.stream().collect(joiner) + "\n Actual: \n" +
                                   actualOutput.stream().collect(joiner));
            }
        }

    }

    private Collector<? super String, StringBuilder, String> joinWith(String separator) {
        return Collector.of(StringBuilder::new,
                            (builder, string) -> builder.append(separator).append(string),
                            (builder1, builder2) -> builder1.append(separator).append(builder2.toString()),
                            StringBuilder::toString);
    }

    private boolean hasExpectedLexErrors(File file, List<String> errorCollector, TestErrorListener lexErrorListener) throws FileNotFoundException
    {
        File lexErrors = replaceExtension(file, "lexerror");
        if(lexErrorListener.hasErrors())
        {
            // for now, just check the file exists. we'll verify its contents later.
            if(!lexErrors.exists())
            {
                errorCollector.add(String.format("Expected no lex errors for %s", file.getName()));
            }
            else
            {
                assertThat(firstLine(lexErrors), is(lexErrorListener.firstError()));
                return true;
            }
        }
        else
        {
            // for now, just check the file exists. we'll verify its contents later.
            if(lexErrors.exists())
            {
                errorCollector.add(String.format("Expected lex errors for %s", file.getName()));
            }
        }
        return false;
    }


    private boolean hasExpectedParseErrors(File file, List<String> errorCollector, TestErrorListener parseErrorListener) throws FileNotFoundException
    {
        File parseerrors = replaceExtension(file, "parseerror");
        if(parseErrorListener.hasErrors())
        {
            // for now, just check the file exists. we'll verify its contents later
            if(!parseerrors.exists())
            {
                errorCollector.add(String.format("Expected no parse errors for %s", file.getName()));
            }
            else
            {
                assertThat(firstLine(parseerrors), is(parseErrorListener.firstError()));
                // checking of contents goes here, then escape early
                return true;
            }
        }
        else
        {
            if(parseerrors.exists())
            {
                errorCollector.add(String.format("Expected parse errors for %s", file.getName()));
            }
        }
        return false;
    }
    private boolean hasExpectedRuntimeErrors(File file, List<String> errorCollector, RuntimeException ex) throws FileNotFoundException
    {
        File runtimeErrors = replaceExtension(file, "runtimeerror");
        // for now, just check the file exists. we'll verify its contents later
        if(!runtimeErrors.exists())
        {
            errorCollector.add(String.format("Expected no runtime errors for %s, but got %s: %s",
                    file.getName(),
                    ex,
                    Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(joinWith("\n"))));
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean hasExpectedNameErrors(File file, List<String> errorCollector, NameResolver nameResolver) throws FileNotFoundException
    {
        File nameerrors = replaceExtension(file, "nameerror");
        if(!nameResolver.getNameResolutionErrors().isEmpty())
        {
            // for now, just check the file exists. we'll verify its contents later
            if(!nameerrors.exists())
            {
                errorCollector.add(String.format("Expected no name errors for %s, got: %s",
                                                 file.getName(),
                                                 nameResolver.getNameResolutionErrors()));
            }
            else
            {
                assertThat("Name error in " + file.getName(), firstLine(nameerrors), is(nameResolver.getNameResolutionErrors().get(0)));
                return true;
            }
        }
        else
        {
            if(nameerrors.exists())
            {
                errorCollector.add(String.format("Expected name errors for %s", file.getName()));
            }
        }
        return false;
    }


    private File replaceExtension(File file, String newExtension)
    {
        return new File(file.getAbsolutePath().replace(".minstrel", "." + newExtension));
    }
}
