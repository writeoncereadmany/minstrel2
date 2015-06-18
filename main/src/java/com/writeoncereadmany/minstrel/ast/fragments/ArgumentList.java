package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;

import java.util.List;

public class ArgumentList implements AstNode
{
    private final List<Expression> expressions;

    public ArgumentList(List<Expression> expressions)
    {
        this.expressions = expressions;
    }
}
