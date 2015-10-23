package com.writeoncereadmany.minstrel.harness.builtins;


import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.PlusFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.PrintFunction;
import com.writeoncereadmany.minstrel.runtime.values.primitives.Atom;

public class Builtins
{
    public static final Terminal PRINT_FUNCTION = new Terminal("print", -1, -1);
    public static final Terminal PLUS_FUNCTION = new Terminal("plus", -1, -1);
    public static final Terminal FUNCTION_TYPE = new Terminal("Function", -1, -1);
    public static final Terminal NUMBER_TYPE = new Terminal("Number", -1, -1);
    public static final Terminal STRING_TYPE = new Terminal("String", -1, -1);

    public static final Value SUCCESS = new Atom("Success");

    public static void defineBuiltins(NameResolver resolver)
    {
        resolver.define(STRING_TYPE, Kind.TYPE);
        resolver.define(NUMBER_TYPE, Kind.TYPE);
        resolver.define(FUNCTION_TYPE, Kind.TYPE);

        resolver.define(PRINT_FUNCTION, Kind.VALUE);
        resolver.define(PLUS_FUNCTION, Kind.VALUE);
    }

    public static Environment getPrelude(NameResolver nameResolver)
    {
        Environment prelude = new Environment();
        prelude.declare(nameResolver.lookup(PRINT_FUNCTION, Kind.VALUE), new PrintFunction(System.out, SUCCESS));
        prelude.declare(nameResolver.lookup(PLUS_FUNCTION, Kind.VALUE), new PlusFunction());
        return prelude;
    }
}
