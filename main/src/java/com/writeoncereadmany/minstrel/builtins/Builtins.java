package com.writeoncereadmany.minstrel.builtins;


import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.concerns.Implementation;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.*;
import com.writeoncereadmany.minstrel.runtime.values.primitives.Atom;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Builtins
{
    public static final Terminal PRINT_FUNCTION = new Terminal("print", -1, -1);
    public static final Terminal PLUS_FUNCTION = new Terminal("plus", -1, -1);
    public static final Terminal MINUS_FUNCTION = new Terminal("minus", -1, -1);
    public static final Terminal MULTIPLY_FUNCTION = new Terminal("multiply", -1, -1);
    public static final Terminal DIVIDE_FUNCTION = new Terminal("divide", -1, -1);
    public static final Terminal NEGATE_FUNCTION = new Terminal("negate", -1, -1);
    public static final Terminal NUMBER_TYPE = new Terminal("Number", -1, -1);
    public static final Terminal STRING_TYPE = new Terminal("String", -1, -1);
    public static final Terminal SUCCESS_ATOM = new Terminal("Success", -1, -1);

    public static final Value SUCCESS = new Atom("Success");

    public static void defineBuiltins(NameResolver resolver)
    {
        defineTypes(resolver, STRING_TYPE, NUMBER_TYPE);
        defineValues(resolver, PRINT_FUNCTION, PLUS_FUNCTION, MINUS_FUNCTION,
                               MULTIPLY_FUNCTION, DIVIDE_FUNCTION, NEGATE_FUNCTION);
        defineAtoms(resolver, SUCCESS_ATOM);
    }

    private static void defineTypes(NameResolver resolver, Terminal... types)
    {
        for(Terminal typeName: types)
        {
            resolver.define(typeName, Kind.TYPE);
        }
    }

    private static void defineValues(NameResolver resolver, Terminal... values)
    {
        for(Terminal valueName: values)
        {
            resolver.define(valueName, Kind.VALUE);
        }
    }

    private static void defineAtoms(NameResolver resolver, Terminal... atoms)
    {
        for(Terminal atomName: atoms)
        {
            resolver.define(atomName, Kind.TYPE);
            resolver.define(atomName, Kind.VALUE);
        }
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
        prelude.declare(nameResolver.lookup(SUCCESS_ATOM, Kind.VALUE), SUCCESS);
        return prelude;
    }

    public static void definePreludeTypes(NameResolver nameResolver, Map<ScopeIndex, Type> types)
    {
        defineAtomType(nameResolver, types, SUCCESS_ATOM);
        defineFunctionType(nameResolver, types, PRINT_FUNCTION, SUCCESS_ATOM, STRING_TYPE);
        defineFunctionType(nameResolver, types, PLUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(nameResolver, types, MINUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(nameResolver, types, MULTIPLY_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(nameResolver, types, DIVIDE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(nameResolver, types, NEGATE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE);
    }

    private static void defineFunctionType(NameResolver nameResolver,
                                           Map<ScopeIndex, Type> types,
                                           Terminal function,
                                           Terminal returnType,
                                           Terminal... parameterTypes)
    {
        types.put(nameResolver.lookup(function, Kind.VALUE),
                  new Type(new FunctionType(stream(parameterTypes).map(p -> nameResolver.lookup(p, Kind.TYPE))
                                                                  .map(ConcreteTypeDefinition::new)
                                                                  .collect(toList()),
                                            new ConcreteTypeDefinition(nameResolver.lookup(returnType, Kind.TYPE)))));
    }



    private static void defineAtomType(NameResolver nameResolver, Map<ScopeIndex, Type> types, Terminal atom) {
        types.put(nameResolver.lookup(atom, Kind.VALUE),
                  new Type(new Implementation(nameResolver.lookup(atom, Kind.TYPE))));
    }
}
