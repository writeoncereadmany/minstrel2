package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.Optional;
import java.util.stream.Stream;

public class Implementation implements Concern
{
    private final ScopeIndex definition;

    public Implementation(ScopeIndex definition)
    {
        this.definition = definition;
    }

    public Stream<TypeError> isSubtype(Optional<Implementation> ofSubtype)
    {
        return ofSubtype.map(this::checkTypesMatch).orElse(Stream.of(new TypeError("Specifying an implementation on the subtype where none is specified on the supertype")));
    }

    private Stream<TypeError> checkTypesMatch(Implementation implementation)
    {
        return implementation.definition.equals(this.definition) ? Stream.empty() : Stream.of(new TypeError("Different implementations are specified on both types"));
    }
}
