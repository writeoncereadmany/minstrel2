package com.writeoncereadmany.minstrel.harness;

import com.writeoncereadmany.minstrel.parser.MinstrelLauncher;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public void allScriptsHaveExpectedResults()
    {
        final List<String> errorCollector = new ArrayList<>();
        testScriptsIn(ROOT_SCRIPT_DIR, errorCollector);
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
            MinstrelLauncher launcher = new MinstrelLauncher(lexErrorListener, parseErrorListener);
            ParseTree parseTree = launcher.parse(launcher.lex(new FileInputStream(file)));

            if(lexErrorListener.hasErrors())
            {
                // for now, just check the file exists. we'll verify its contents later.
                if(!replaceExtension(file, "lexerror").exists())
                {
                    errorCollector.add(String.format("Expected no lex errors for %s", file.getName()));
                }
            }

            if(parseErrorListener.hasErrors())
            {
                // for now, just check the file exists. we'll verify its contents later
                errorCollector.add(String.format("Expected no parse errors for %s", file.getName()));
            }

        } catch (IOException ex)
        {
            throw new RuntimeException("Failed to load file", ex);
        }
    }

    private File replaceExtension(File file, String newExtension)
    {
        return new File(file.getAbsolutePath().replace(".minstrel", "." + newExtension));
    }
}
