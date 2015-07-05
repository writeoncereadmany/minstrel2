package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.types.defintions.TypeDefinition;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionTypingRules implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target, TypeChecker checker)
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
        return Stream.concat(getArgumentTypeMismatches(targetType, sourceType, checker),
                             getReturnTypeMismatches(checker, sourceType, targetType));
    }

    private Stream<TypeError> getReturnTypeMismatches(TypeChecker checker, FunctionType sourceType, FunctionType targetType)
    {
        TypeDefinition sourceReturnType = sourceType.returnType;
        TypeDefinition targetReturnType = targetType.returnType;
        return checker.canAssign(sourceReturnType, targetReturnType);
    }

    private Stream<TypeError> getArgumentTypeMismatches(FunctionType targetType, FunctionType sourceType, TypeChecker checker)
    {
        return IntStream.range(0, targetType.argumentTypes.size())
                        .boxed()
                        .flatMap(i -> {
                            TypeDefinition sourceArgType = sourceType.argumentTypes.get(i);
                            TypeDefinition targetArgType = targetType.argumentTypes.get(i);
                            return checker.canAssign(sourceArgType, targetArgType);
                        });
    }
}
