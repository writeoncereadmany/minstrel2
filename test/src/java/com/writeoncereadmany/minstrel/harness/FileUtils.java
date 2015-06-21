package com.writeoncereadmany.minstrel.harness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils
{
    public static List<String> readLines(File lexerrors) throws FileNotFoundException
    {
        List<String> expected = new ArrayList<>();
        Scanner scanner = new Scanner(lexerrors);
        while(scanner.hasNext())
        {
            expected.add(scanner.nextLine());
        }
        return expected;
    }
}
