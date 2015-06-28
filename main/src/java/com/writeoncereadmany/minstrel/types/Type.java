package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.types.concerns.Concern;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Type
{
    private final Map<Class<? extends Concern>, Concern> concerns;

    public Type(Concern... concerns)
    {
        this.concerns = stream(concerns).collect(Collectors.toMap(concern -> concern.getClass(), concern -> concern));
    }

    public List<TypeError> isAssignableTo(Type target, List<TypingRule> typingRules)
    {
        return typingRules.stream().flatMap(rule -> rule.isAssignableTo(this, target)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends Concern> Optional<T> getConcern(Class<T> concernType)
    {
        return Optional.ofNullable((T)concerns.get(concernType));
    }
}
