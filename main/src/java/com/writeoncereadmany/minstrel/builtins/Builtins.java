package com.writeoncereadmany.minstrel.builtins;


import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.concerns.Implementation;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.*;
import com.writeoncereadmany.minstrel.runtime.values.primitives.Atom;

import java.io.PrintStream;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Builtins
{
    public static final Terminal PRINT_FUNCTION = new Terminal("print", -1, 0);
    public static final Terminal PLUS_FUNCTION = new Terminal("plus", -2, 0);
    public static final Terminal MINUS_FUNCTION = new Terminal("minus", -3, 0);
    public static final Terminal MULTIPLY_FUNCTION = new Terminal("multiply", -4, 0);
    public static final Terminal DIVIDE_FUNCTION = new Terminal("divide", -5, 0);
    public static final Terminal NEGATE_FUNCTION = new Terminal("negate", -6, 0);
    public static final Terminal NUMBER_TYPE = new Terminal("Number", -7, 0);
    public static final Terminal STRING_TYPE = new Terminal("String", -8, 0);
    public static final Terminal SUCCESS_TYPE = new Terminal("Success", -9, 0);
    public static final Terminal SUCCESS_ATOM = new Terminal("Success", -10, 0);

    public static final Value SUCCESS = new Atom("Success");

    public static void defineBuiltins(NameResolver resolver)
    {
        defineTypes(resolver, STRING_TYPE, NUMBER_TYPE, SUCCESS_TYPE);
        defineValues(resolver, PRINT_FUNCTION, PLUS_FUNCTION, MINUS_FUNCTION,
                               MULTIPLY_FUNCTION, DIVIDE_FUNCTION, NEGATE_FUNCTION, SUCCESS_ATOM);

    }

    private static void defineTypes(NameResolver resolver, Terminal... types)
    {
        for(Terminal typeName: types)
        {
            resolver.define(typeName, Kind.TYPE);
            resolver.resolve(typeName, Kind.TYPE);
        }
    }

    private static void defineValues(NameResolver resolver, Terminal... values)
    {
        for(Terminal valueName: values)
        {
            resolver.define(valueName, Kind.VALUE);
            resolver.resolve(valueName, Kind.VALUE);
        }
    }

    public static Environment getPrelude(PrintStream printStream)
    {
        Environment prelude = new Environment();
        prelude.declare(PRINT_FUNCTION.scopeIndex(), new PrintFunction(printStream, SUCCESS));
        prelude.declare(PLUS_FUNCTION.scopeIndex(), new PlusFunction());
        prelude.declare(MINUS_FUNCTION.scopeIndex(), new MinusFunction());
        prelude.declare(MULTIPLY_FUNCTION.scopeIndex(), new MultipliedByFunction());
        prelude.declare(DIVIDE_FUNCTION.scopeIndex(), new DividedByFunction());
        prelude.declare(NEGATE_FUNCTION.scopeIndex(), new NegateFunction());
        prelude.declare(SUCCESS_ATOM.scopeIndex(), SUCCESS);
        return prelude;
    }

    public static void definePreludeTypes(Map<ScopeIndex, Type> types)
    {
        defineObjectType(types, SUCCESS_ATOM, SUCCESS_TYPE);
        defineFunctionType(types, PRINT_FUNCTION, SUCCESS_ATOM, STRING_TYPE);
        defineFunctionType(types, PLUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(types, MINUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(types, MULTIPLY_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(types, DIVIDE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(types, NEGATE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE);
    }

    private static void defineFunctionType(Map<ScopeIndex, Type> types,
                                           Terminal function,
                                           Terminal returnType,
                                           Terminal... parameterTypes)
    {
        types.put(function.scopeIndex(),
                  new Type(new FunctionType(stream(parameterTypes).map(Terminal::scopeIndex)
                                                                  .map(ConcreteTypeDefinition::new)
                                                                  .collect(toList()),
                                            new ConcreteTypeDefinition(returnType.scopeIndex()))));
    }

    private static void defineObjectType(Map<ScopeIndex, Type> types, Terminal value, Terminal type)
    {
        types.put(value.scopeIndex(), new Type(new Implementation(type.scopeIndex())));
    }
}
