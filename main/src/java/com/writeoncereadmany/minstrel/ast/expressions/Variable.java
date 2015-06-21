package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.names.NameResolver;
import com.writeoncereadmany.minstrel.names.ScopeIndex;

public class Variable implements Expression
{
    private final String name;
    private ScopeIndex index;

    public Variable(String name)
    {
        this.name = name;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        // can't define anything
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        index = nameResolver.resolveValue(name);
    }
}
