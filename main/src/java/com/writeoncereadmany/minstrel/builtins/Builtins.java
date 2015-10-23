package com.writeoncereadmany.minstrel.builtins;


import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.*;
import com.writeoncereadmany.minstrel.runtime.values.primitives.Atom;

import java.io.PrintStream;

public class Builtins
{
    public static final Terminal PRINT_FUNCTION = new Terminal("print", -1, -1);
    public static final Terminal PLUS_FUNCTION = new Terminal("plus", -1, -1);
    public static final Terminal MINUS_FUNCTION = new Terminal("minus", -1, -1);
    public static final Terminal MULTIPLY_FUNCTION = new Terminal("multiply", -1, -1);
    public static final Terminal DIVIDE_FUNCTION = new Terminal("divide", -1, -1);
    public static final Terminal NEGATE_FUNCTION = new Terminal("negate", -1, -1);
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
        resolver.define(MINUS_FUNCTION, Kind.VALUE);
        resolver.define(MULTIPLY_FUNCTION, Kind.VALUE);
        resolver.define(DIVIDE_FUNCTION, Kind.VALUE);
        resolver.define(NEGATE_FUNCTION, Kind.VALUE);
    }

    public static Environment getPrelude(NameResolver nameResolver, PrintStream printStream)
    {
        Environment prelude = new Environment();
        prelude.declare(nameResolver.lookup(PRINT_FUNCTION, Kind.VALUE), new PrintFunction(printStream, SUCCESS));
        prelude.declare(nameResolver.lookup(PLUS_FUNCTION, Kind.VALUE), new PlusFunction());
        prelude.declare(nameResolver.lookup(MINUS_FUNCTION, Kind.VALUE), new MinusFunction());
        prelude.declare(nameResolver.lookup(MULTIPLY_FUNCTION, Kind.VALUE), new MultipliedByFunction());
        prelude.declare(nameResolver.lookup(DIVIDE_FUNCTION, Kind.VALUE), new DividedByFunction());
        prelude.declare(nameResolver.lookup(NEGATE_FUNCTION, Kind.VALUE), new NegateFunction());
        return prelude;
    }
}
