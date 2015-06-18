package com.writeoncereadmany.minstrel.ast.expressions;

public class PlusExpression implements Expression
{
    private final Expression augend;
    private final Expression addend;

    public PlusExpression(Expression augend, Expression addend)
    {
        this.augend = augend;
        this.addend = addend;
    }
}
