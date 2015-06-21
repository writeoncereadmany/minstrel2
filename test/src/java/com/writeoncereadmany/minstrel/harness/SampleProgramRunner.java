package com.writeoncereadmany.minstrel.harness;

import com.writeoncereadmany.minstrel.ast.Program;
import com.writeoncereadmany.minstrel.names.NameResolver;
import com.writeoncereadmany.minstrel.orchestrator.MinstrelOrchestrator;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.writeoncereadmany.minstrel.harness.FileUtils.readLines;
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
    @Ignore
    public void testASingleScript() throws Exception
    {
        final List<String> errorCollector = new ArrayList<>();
        runFileAndVerifyResults(new File(ROOT_SCRIPT_DIR, "lexing/basic_lex_error.minstrel"), errorCollector);
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

            program.defineNames(nameResolver);
            program.resolveNames(nameResolver);

            if(hasExpectedNameErrors(file, errorCollector, nameResolver))
            {
                return;
            }


        } catch (IOException ex)
        {
            throw new RuntimeException("Failed to load file", ex);
        } catch (RuntimeException ex)
        {
            throw new RuntimeException("Error when parsing " + file.getName(), ex);
        }
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
                List<String> expected = readLines(lexErrors);
                assertThat(expected, is(lexErrorListener.errors()));
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


    private boolean hasExpectedParseErrors(File file, List<String> errorCollector, TestErrorListener parseErrorListener)
    {
        if(parseErrorListener.hasErrors())
        {
            // for now, just check the file exists. we'll verify its contents later
            if(!replaceExtension(file, "parseerror").exists())
            {
                errorCollector.add(String.format("Expected no parse errors for %s", file.getName()));
            }
            else
            {
                // checking of contents goes here, then escape early
                return true;
            }
        }
        else
        {
            if(replaceExtension(file, "parseerror").exists())
            {
                errorCollector.add(String.format("Expected parse errors for %s", file.getName()));
            }
        }
        return false;
    }

    private boolean hasExpectedNameErrors(File file, List<String> errorCollector, NameResolver nameResolver)
    {
        if(!nameResolver.getNameResolutionErrors().isEmpty())
        {
            // for now, just check the file exists. we'll verify its contents later
            if(!replaceExtension(file, "nameerror").exists())
            {
                errorCollector.add(String.format("Expected no name errors for %s, got: %s",
                                                 file.getName(),
                                                 nameResolver.getNameResolutionErrors()));
            }
            else
            {
                // checking of contents goes here, then escape early
                return true;
            }
        }
        else
        {
            if(replaceExtension(file, "nameerror").exists())
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
