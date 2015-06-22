package com.writeoncereadmany.minstrel.harness.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils
{
    public static String firstLine(File file) throws FileNotFoundException
    {
        return new Scanner(file).nextLine();
    }
}
