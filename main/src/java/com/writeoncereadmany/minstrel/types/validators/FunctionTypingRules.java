package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.FunctionType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionTypingRules implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target, Function<ScopeIndex, Type> provider, List<TypingRule> rules)
    {
        Optional<FunctionType> sourceType = source.getConcern(FunctionType.class);
        Optional<FunctionType> targetType = target.getConcern(FunctionType.class);

        return targetType.map(f -> f.isSupertypeOf(sourceType, provider, rules)).orElse(Stream.empty());
    }
}
