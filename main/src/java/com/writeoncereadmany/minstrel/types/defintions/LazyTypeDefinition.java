package com.writeoncereadmany.minstrel.types.defintions;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeDefinition;

import java.util.function.Function;

public class LazyTypeDefinition implements TypeDefinition
{
    private final TypeDefinition delegate;
    private Type type;

    public LazyTypeDefinition(TypeDefinition delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public Type getType(Function<ScopeIndex, Type> typeDefinitions)
    {
        if(type == null)
        {
            type = delegate.getType(typeDefinitions);
        }
        return type;
    }
}
