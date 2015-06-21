package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class ArgumentList implements AstNode
{
    private final List<Expression> expressions;

    public ArgumentList(List<Expression> expressions)
    {
        this.expressions = expressions;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        expressions.forEach(expression -> expression.defineNames(nameResolver));
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        expressions.forEach(expression -> expression.resolveNames(nameResolver));
    }
}
