package com.writeoncereadmany.minstrel.harness;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import com.writeoncereadmany.minstrel.compile.types.validators.InterfaceRule;
import com.writeoncereadmany.minstrel.compile.visitors.DefineNames;
import com.writeoncereadmany.minstrel.compile.visitors.ResolveNames;
import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.visitors.TypeChecker;
import com.writeoncereadmany.minstrel.harness.utils.ErrorChecker;
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
import java.util.stream.Stream;

import static com.writeoncereadmany.minstrel.harness.utils.ErrorChecker.collateErrors;
import static com.writeoncereadmany.minstrel.harness.utils.FileUtils.firstLine;
import static com.writeoncereadmany.util.Joiner.joinWith;
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
        runFileAndVerifyResults(new File(ROOT_SCRIPT_DIR, "implemented/typechecking/wrong_arity.minstrel"), errorCollector);
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

            if (collateErrors("lexerror", file.toPath(), lexErrorListener.errors()::stream, errorCollector::add))
            {
                return;
            }

            if (collateErrors("parseerror", file.toPath(), parseErrorListener.errors()::stream, errorCollector::add))
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

            if(collateErrors("nameerror", file.toPath(), nameResolver.getNameResolutionErrors()::stream, errorCollector::add))
            {
                return;
            }

            Map<ScopeIndex, Type> typeDefinitions = new HashMap<>();

            Builtins.definePreludeTypes(typeDefinitions);

            TypeEngine typeEngine = new TypeEngine(asList(new FunctionRules(), new InterfaceRule(), new ImplementationRule()),
                                                   typeDefinitions);

            TypeChecker typeChecker = new TypeChecker(typeEngine);
            program.visit(typeChecker);

            List<TypeError> typeErrors = typeChecker.getTypeErrors();

            if(collateErrors("typeerror", file.toPath(), () -> typeErrors.stream().map(TypeError::toString), errorCollector::add))
            {
                return;
            }

            ByteArrayOutputStream printed = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(printed);
            try
            {
                Interpreter interpreter = new Interpreter(nameResolver, Builtins.getPrelude(printStream));
                program.visit(interpreter);
            }
            catch (RuntimeException ex)
            {
                if (collateErrors("runtimeerror", file.toPath(), () -> Stream.of(ex.toString()), errorCollector::add))
                {
                    return;
                }
            }

            if(!printed.toString().isEmpty())
            {
                collateErrors("out", file.toPath(), () -> Stream.of(printed.toString().split("\n")), errorCollector::add);
            }
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
}
