package com.writeoncereadmany.minstrel.compile.types.validators;

import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionRules implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(StructuralType source, StructuralType target, TypeEngine checker)
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
        return Stream.concat(getArgumentTypeMismatches(sourceType, targetType, checker),
                             getReturnTypeMismatches(sourceType, targetType, checker));
    }

    private Stream<TypeError> getReturnTypeMismatches(FunctionType sourceType, FunctionType targetType, TypeEngine checker)
    {
        TypeDefinition sourceReturnType = sourceType.returnType;
        TypeDefinition targetReturnType = targetType.returnType;
        return checker.canAssign(sourceReturnType, targetReturnType);
    }

    private Stream<TypeError> getArgumentTypeMismatches(FunctionType sourceType, FunctionType targetType, TypeEngine checker)
    {
        return IntStream.range(0, targetType.argumentTypes.size())
                        .boxed()
                        .flatMap(i -> {
                            TypeDefinition sourceArgType = sourceType.argumentTypes.get(i);
                            TypeDefinition targetArgType = targetType.argumentTypes.get(i);
                            return checker.canAssign(targetArgType, sourceArgType);
                        });
    }
}
