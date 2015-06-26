package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class StringLiteral implements Expression
{
    private final Terminal value;

    public StringLiteral(Terminal value)
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
