package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Implementation implements Concern
{
    private final Set<ScopeIndex> possibleImplementations;

    public Implementation(ScopeIndex... implementations)
    {
        if(implementations.length == 0)
        {
            throw new IllegalArgumentException("An implementation with no possible implementations contains no instances");
        }
        this.possibleImplementations = stream(implementations).collect(Collectors.toSet());
    }

    public Stream<TypeError> isSupersetOf(Optional<Implementation> otherImplementations)
    {
        return otherImplementations.map(that -> that.possibleImplementations
                .stream()
                .flatMap((ScopeIndex si) -> possibleImplementations.contains(si)
                        ? Stream.empty()
                        : Stream.of(new TypeError("An implementation required by the supertype is not"))))
                .orElse(Stream.of(new TypeError("Specifying an implementation on the supertype where none is specified on the subtype")));
    }
}
