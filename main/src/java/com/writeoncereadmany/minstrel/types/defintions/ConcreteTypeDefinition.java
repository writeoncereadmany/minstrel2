package com.writeoncereadmany.minstrel.types.defintions;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeDefinition;

import java.util.function.Function;

public class ConcreteTypeDefinition implements TypeDefinition
{
    private final ScopeIndex index;

    public ConcreteTypeDefinition(ScopeIndex index)
    {
        this.index = index;
    }

    @Override
    public Type getType(Function<ScopeIndex, Type> typeDefinitions)
    {
        return typeDefinitions.apply(index);
    }
}
