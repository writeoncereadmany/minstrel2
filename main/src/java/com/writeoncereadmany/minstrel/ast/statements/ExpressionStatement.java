package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class ExpressionStatement implements Statement
{
    private final Expression expression;

    public ExpressionStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        expression.defineNames(nameResolver);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        expression.resolveNames(nameResolver);
    }
}
