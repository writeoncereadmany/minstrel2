package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;

import java.util.Objects;

public class ConcreteTypeDefinition implements TypeDefinition
{
    private final ScopeIndex index;
    private final String name;

    public ConcreteTypeDefinition(ScopeIndex index, String name)
    {
        this.index = index;
        this.name = name;
    }

    public static ConcreteTypeDefinition fromTerminal(Terminal terminal)
    {
        return new ConcreteTypeDefinition(terminal.scopeIndex(), terminal.text);
    }

    @Override
    public Type getType(TypeEngine engine)
    {
        return engine.lookupNamedType(index);
    }

    @Override
    public TypeDefinition returnType()
    {
        return new ReturnTypeDefinition(this);
    }

    @Override
    public TypeDefinition getMember(String member)
    {
        return new MemberTypeDefinition(this, member);
    }

    @Override
    public String describe() {
        return name;
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
