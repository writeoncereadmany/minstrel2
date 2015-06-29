package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeDefinition;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionType implements Concern
{
    private final List<TypeDefinition> argumentTypes;
    private final TypeDefinition returnType;

    public FunctionType(List<TypeDefinition> argumentTypes, TypeDefinition returnType)
    {
        this.argumentTypes = argumentTypes;
        this.returnType = returnType;
    }

    public Stream<TypeError> isSupertypeOf(Optional<FunctionType> sourceType, Function<ScopeIndex, Type> provider, List<TypingRule> rules)
    {
        return sourceType.map(s -> isSupertypeOf(s, provider, rules))
                         .orElse(Stream.of(new TypeError("Cannot assign a non-function type to a function type")));
    }

    private Stream<TypeError> isSupertypeOf(FunctionType potentialSubtype, Function<ScopeIndex, Type> provider, List<TypingRule> rules)
    {
        int myLength = argumentTypes.size();
        int hisLength = potentialSubtype.argumentTypes.size();
        if(myLength != hisLength)
        {
            return Stream.of(new TypeError("Cannot assign a function of arity " + hisLength + " to a function of arity " + myLength));
        }
        return Stream.concat(argumentErrors(potentialSubtype), returnTypeErrors(potentialSubtype, provider, rules));
    }

    private Stream<TypeError> returnTypeErrors(FunctionType potentialSubtype, Function<ScopeIndex, Type> provider, List<TypingRule> rules)
    {
        return returnType.getType(provider).isAssignableTo(potentialSubtype.returnType.getType(provider), rules, provider);
    }

    private Stream<TypeError> argumentErrors(FunctionType potentialSubtype)
    {
        return Stream.empty();
    }
}
