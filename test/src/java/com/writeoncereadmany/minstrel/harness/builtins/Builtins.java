package com.writeoncereadmany.minstrel.harness.builtins;


import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;

public class Builtins
{
    public static void defineBuiltins(NameResolver resolver)
    {
        Terminal name2 = new Terminal("String", -1, -1);
        resolver.define(name2, Kind.TYPE);
        Terminal name1 = new Terminal("Number", -1, -1);
        resolver.define(name1, Kind.TYPE);
        Terminal name = new Terminal("Function", -1, -1);
        resolver.define(name, Kind.TYPE);

        Terminal name4 = new Terminal("print", -1, -1);
        resolver.define(name4, Kind.VALUE);
        Terminal name3 = new Terminal("plus", -1, -1);
        resolver.define(name3, Kind.VALUE);
    }
}
