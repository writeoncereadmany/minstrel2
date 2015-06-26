package com.writeoncereadmany.minstrel.harness.builtins;


import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class Builtins
{
    public static void defineBuiltins(NameResolver resolver)
    {
        resolver.defineType(new Terminal("String", -1, -1));
        resolver.defineType(new Terminal("Number", -1, -1));
        resolver.defineType(new Terminal("Function", -1, -1));

        resolver.defineValue(new Terminal("print", -1, -1));
    }
}
