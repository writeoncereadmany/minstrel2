package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.names.NameResolver;

public class PlusExpression implements Expression
{
    private final Expression augend;
    private final Expression addend;

    public PlusExpression(Expression augend, Expression addend)
    {
        this.augend = augend;
        this.addend = addend;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        augend.defineNames(nameResolver);
        addend.defineNames(nameResolver);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        augend.resolveNames(nameResolver);
        addend.resolveNames(nameResolver);
    }
}
