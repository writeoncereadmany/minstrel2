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

    public Stream<TypeError> isAssignableTo(Type target, TypeEngine engine)
    {
        return engine.getRules().stream().flatMap(rule -> rule.isAssignableTo(this, target, engine));
    }

    @SuppressWarnings("unchecked")
    public <T extends Concern> T getConcern(Class<T> concernType)
    {
        return (T)concerns.get(concernType);
    }
}
