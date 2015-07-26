package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;

import java.util.Objects;

public class ConcreteTypeDefinition implements TypeDefinition
{
    private final ScopeIndex index;

    public ConcreteTypeDefinition(ScopeIndex index)
    {
        this.index = index;
    }

    @Override
    public Type getType(TypeChecker engine)
    {
        return engine.lookupType(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcreteTypeDefinition that = (ConcreteTypeDefinition) o;
        return Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}