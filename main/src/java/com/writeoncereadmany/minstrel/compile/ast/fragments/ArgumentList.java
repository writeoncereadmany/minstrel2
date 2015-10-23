package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ArgumentList implements AstNode
{
    public final List<Expression> expressions;

    public ArgumentList(List<Expression> expressions)
    {
        this.expressions = unmodifiableList(expressions);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitArgumentList(expressions);
    }

}
