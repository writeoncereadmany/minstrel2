package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class NumberLiteral implements Expression
{
    public NumberLiteral(Terminal value)
    {

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
