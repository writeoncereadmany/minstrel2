package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;

public class ExpressionStatement implements Statement
{
    private final Expression expression;

    public ExpressionStatement(Expression expression)
    {
        this.expression = expression;
    }
}
