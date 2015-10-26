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
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Builtins
{
    public static final Terminal PRINT_FUNCTION = new Terminal("print", -1, 0);
    public static final Terminal PLUS_FUNCTION = new Terminal("$+2", -2, 0);
    public static final Terminal MINUS_FUNCTION = new Terminal("$-2", -3, 0);
    public static final Terminal MULTIPLY_FUNCTION = new Terminal("$*2", -4, 0);
    public static final Terminal DIVIDE_FUNCTION = new Terminal("$/2", -5, 0);
    public static final Terminal NEGATE_FUNCTION = new Terminal("$-1", -6, 0);
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

    public static void definePreludeTypes(Map<ScopeIndex, Type> typeDefinitions)
    {
        defineSingleImplementationType(typeDefinitions, NUMBER_TYPE);
        defineSingleImplementationType(typeDefinitions, STRING_TYPE);
        defineSingleImplementationType(typeDefinitions, SUCCESS_TYPE);
    }

    private static Type defineSingleImplementationType(Map<ScopeIndex, Type> typeDefinitions, Terminal type) {
        return typeDefinitions.put(type.scopeIndex(), new Type(new Implementation(NUMBER_TYPE.scopeIndex())));
    }

    public static void defineTypesOfPreludeValues(Map<ScopeIndex, TypeDefinition> typesOfValues)
    {
        defineTypeOfObjectWithNamedType(typesOfValues, SUCCESS_ATOM, SUCCESS_TYPE);
        defineFunctionType(typesOfValues, PRINT_FUNCTION, SUCCESS_ATOM, STRING_TYPE);
        defineFunctionType(typesOfValues, PLUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, MINUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, MULTIPLY_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, DIVIDE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, NEGATE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE);
    }

    private static void defineFunctionType(Map<ScopeIndex, TypeDefinition> types,
                                           Terminal function,
                                           Terminal returnType,
                                           Terminal... parameterTypes)
    {
        types.put(function.scopeIndex(),
                  new FunctionType(stream(parameterTypes).map(Terminal::scopeIndex)
                                                         .map(ConcreteTypeDefinition::new)
                                                         .collect(toList()),
                                   new ConcreteTypeDefinition(returnType.scopeIndex())));
    }

    private static void defineTypeOfObjectWithNamedType(Map<ScopeIndex, TypeDefinition> typesOfValues, Terminal value, Terminal type)
    {
        typesOfValues.put(value.scopeIndex(), new ConcreteTypeDefinition(type.scopeIndex()));
    }
}
