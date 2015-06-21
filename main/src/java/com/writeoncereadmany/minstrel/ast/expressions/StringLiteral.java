package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.names.NameResolver;

public class StringLiteral implements Expression
{
    private final String value;

    public StringLiteral(String value)
    {
        this.value = value;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        // name-agnostic
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        // name-agnostic
    }
}
