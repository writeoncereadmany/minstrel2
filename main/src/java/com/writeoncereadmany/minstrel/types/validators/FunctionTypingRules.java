package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeEngine;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.FunctionType;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionTypingRules implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target, TypeEngine engine)
    {
        FunctionType sourceType = source.getConcern(FunctionType.class);
        FunctionType targetType = target.getConcern(FunctionType.class);

        // if we're not asserting the target's a type, we don't care about the source
        if(targetType == null)
        {
            return Stream.empty();
        }
        // if the target's a function, the source needs to be one too
        if(sourceType == null)
        {
            return Stream.of(new TypeError("Can only assign functions to functions"));
        }
        // if we have two functions, then they must have the same arity:
        if(targetType.argumentTypes.size() != sourceType.argumentTypes.size())
        {
            return Stream.of(new TypeError("Cannot assign a function of arity " + sourceType.argumentTypes.size() +
                                           " to a function of arity " + targetType.argumentTypes.size()));
        }
        // if we have same arities, then the source's return type must be assignable to the target's return type,
        // and the target's argument types must each be assignable to the equivalent source's argument types
        Stream<TypeError> argumentMismatches = getArgumentTypeMismatches(targetType, sourceType, engine);
        Type sourceReturnType = sourceType.returnType.getType(engine);
        Type targetReturnType = targetType.returnType.getType(engine);
        return Stream.concat(argumentMismatches, sourceReturnType.isAssignableTo(targetReturnType, engine));
    }

    private Stream<TypeError> getArgumentTypeMismatches(FunctionType targetType, FunctionType sourceType, TypeEngine engine)
    {
        return IntStream.range(0, targetType.argumentTypes.size())
                        .boxed()
                        .flatMap(i -> {
                            Type sourceArgType = sourceType.argumentTypes.get(i).getType(engine);
                            Type targetArgType = targetType.argumentTypes.get(i).getType(engine);
                            return targetArgType.isAssignableTo(sourceArgType, engine);
                        });
    }
}
