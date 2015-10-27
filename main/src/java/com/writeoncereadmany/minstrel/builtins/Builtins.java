package com.writeoncereadmany.minstrel.builtins;


import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.concerns.Concern;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.concerns.Implementation;
import com.writeoncereadmany.minstrel.compile.types.concerns.Interface;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.*;
import com.writeoncereadmany.minstrel.runtime.values.primitives.Atom;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;

import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
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
    public static final Terminal SHOWABLE_TYPE = new Terminal("Showable", -10, 0);
    public static final Terminal SUCCESS_ATOM = new Terminal("Success", -10, 0);

    public static final Value SUCCESS = new Atom("Success");

    public static void defineBuiltins(NameResolver resolver)
    {
        defineTypes(resolver, STRING_TYPE, NUMBER_TYPE, SUCCESS_TYPE, SHOWABLE_TYPE);
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
        TypeDefinition stringDefinition = new ConcreteTypeDefinition(STRING_TYPE.scopeIndex());
        TypeDefinition numberDefinition = new ConcreteTypeDefinition(NUMBER_TYPE.scopeIndex());

        FunctionType nothingToString = new FunctionType(Collections.emptyList(), stringDefinition);
        FunctionType nothingToNumber = new FunctionType(Collections.emptyList(), numberDefinition);
        FunctionType numberToNumber = new FunctionType(Collections.singletonList(numberDefinition), numberDefinition);
        FunctionType stringToString = new FunctionType(Collections.singletonList(stringDefinition), stringDefinition);

        Interface showable = new Interface(mapOf(entry("show", nothingToString)));

        Interface number = new Interface(mapOf(entry("show", nothingToString),
                                                entry("plus", numberToNumber),
                                                entry("minus", numberToNumber),
                                                entry("multipliedBy", numberToNumber),
                                                entry("dividedBy", numberToNumber),
                                                entry("negate", nothingToNumber)));

        Interface string = new Interface(mapOf(entry("show", nothingToString),
                                                entry("plus", stringToString)));

        typeDefinitions.put(SHOWABLE_TYPE.scopeIndex(), new Type(showable));
        typeDefinitions.put(NUMBER_TYPE.scopeIndex(), new Type(new Implementation(NUMBER_TYPE.scopeIndex()), number));
        typeDefinitions.put(STRING_TYPE.scopeIndex(), new Type(new Implementation(STRING_TYPE.scopeIndex()), string));
        typeDefinitions.put(SUCCESS_TYPE.scopeIndex(), new Type(new Implementation(SUCCESS_TYPE.scopeIndex()), showable));
    }

    public static void defineTypesOfPreludeValues(Map<ScopeIndex, Typed> typesOfValues)
    {
        defineTypeOfObjectWithNamedType(typesOfValues, SUCCESS_ATOM, SUCCESS_TYPE);
        defineFunctionType(typesOfValues, PRINT_FUNCTION, SUCCESS_TYPE, SHOWABLE_TYPE);
        defineFunctionType(typesOfValues, PLUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, MINUS_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, MULTIPLY_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, DIVIDE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE, NUMBER_TYPE);
        defineFunctionType(typesOfValues, NEGATE_FUNCTION, NUMBER_TYPE, NUMBER_TYPE);
    }

    private static void defineFunctionType(Map<ScopeIndex, Typed> types,
                                           Terminal function,
                                           Terminal returnType,
                                           Terminal... parameterTypes)
    {
        types.put(function.scopeIndex(),
                  new FunctionTypeLiteral(new TypeList(stream(parameterTypes).map(NamedType::new)
                                                         .collect(toList())),
                                   new NamedType(returnType)));
    }

    private static void defineTypeOfObjectWithNamedType(Map<ScopeIndex, Typed> typesOfValues, Terminal value, Terminal type)
    {
        typesOfValues.put(value.scopeIndex(), new NamedType(type));
    }
}
