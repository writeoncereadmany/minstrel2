package com.writeoncereadmany.minstrel.harness.utils;

import com.writeoncereadmany.util.Pair;
import com.writeoncereadmany.util.Zipper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.writeoncereadmany.util.Joiner.joinWith;

public class ErrorChecker
{
    public static boolean collateErrors(String errorFileExtension, Path codeFile, Supplier<Stream<String>> actualErrors, Consumer<String> errorCollector) throws IOException
    {
        final Path withErrorFileExtention = Paths.get(codeFile.toAbsolutePath().toString().replace(".minstrel", "." + errorFileExtension));
        boolean expectErrors = Files.exists(withErrorFileExtention);
        boolean gotErrors = actualErrors.get().findAny().isPresent();
        if(expectErrors && !gotErrors)
        {
            errorCollector.accept("Expected the following errors when running " + codeFile.toString() + ": " + Files.lines(withErrorFileExtention).collect(joinWith(", ")));
        }
        else if(!expectErrors && gotErrors)
        {
            errorCollector.accept("Got unexpected errors when running " + codeFile.toString() + ": " + actualErrors.get().collect(joinWith(", ")));
        }
        else if(expectErrors && gotErrors)
        {
            Zipper.zipLongest(Files.lines(withErrorFileExtention), actualErrors.get(), Pair::new, "no more errors", "no more errors")
                  .filter(pair -> !pair.left.equals(pair.right))
                  .map(pair -> "Mismatch in expected errors when running " + codeFile.toString() + ": expected " + pair.left + ", got " + pair.right)
                  .forEach(errorCollector::accept);
        }
        return expectErrors || gotErrors;
    }
}
