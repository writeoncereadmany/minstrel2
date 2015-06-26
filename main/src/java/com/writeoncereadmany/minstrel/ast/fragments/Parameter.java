package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.names.NameResolver;
import com.writeoncereadmany.minstrel.names.ScopeIndex;

public class Parameter implements AstNode
{
    private final Terminal type;
    private final Terminal name;

    private ScopeIndex typeIndex;

    public Parameter(Terminal type, Terminal name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        nameResolver.defineValue(name);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        typeIndex = nameResolver.resolveType(type);
    }
}
