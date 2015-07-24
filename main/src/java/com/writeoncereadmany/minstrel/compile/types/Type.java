package com.writeoncereadmany.minstrel.compile.types;

import com.writeoncereadmany.minstrel.compile.types.concerns.Concern;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Type
{
    private final Map<Class<? extends Concern>, Concern> concerns;

    public Type(Concern... concerns)
    {
        this.concerns = stream(concerns).collect(Collectors.toMap(concern -> concern.getClass(), concern -> concern));
    }

    @SuppressWarnings("unchecked")
    public <T extends Concern> T getConcern(Class<T> concernType)
    {
        return (T)concerns.get(concernType);
    }
}
