package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

public class ArgumentList implements AstNode
{
    private final List<Expression> expressions;

    public ArgumentList(List<Expression> expressions)
    {
        this.expressions = expressions;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitArgumentList(expressions);
    }

}
