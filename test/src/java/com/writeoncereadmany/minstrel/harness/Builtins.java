package com.writeoncereadmany.minstrel.harness;


import com.writeoncereadmany.minstrel.names.NameResolver;

public class Builtins
{
    public static void defineBuiltins(NameResolver resolver)
    {
        resolver.defineType("String");
        resolver.defineType("Number");
        resolver.defineType("Showable");

        resolver.defineValue("print");
    }
}
