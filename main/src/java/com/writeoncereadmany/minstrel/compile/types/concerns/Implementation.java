package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Implementation implements Concern
{
    public final Set<ScopeIndex> possibleImplementations;

    public Implementation(ScopeIndex... implementations)
    {
        if(implementations.length == 0)
        {
            throw new IllegalArgumentException("An implementation with no possible implementations contains no instances");
        }
        this.possibleImplementations = Collections.unmodifiableSet(stream(implementations).collect(Collectors.toSet()));
    }
}
