package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.concerns.Concern;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Type
{
    private final Map<Class<? extends Concern>, Concern> concerns;

    public Type(Concern... concerns)
    {
        this.concerns = stream(concerns).collect(Collectors.toMap(concern -> concern.getClass(), concern -> concern));
    }

    public Stream<TypeError> isAssignableTo(Type target, List<TypingRule> typingRules, Function<ScopeIndex, Type> provider)
    {
        return typingRules.stream().flatMap(rule -> rule.isAssignableTo(this, target, provider, typingRules));
    }

    @SuppressWarnings("unchecked")
    public <T extends Concern> T getConcern(Class<T> concernType)
    {
        return (T)concerns.get(concernType);
    }
}
