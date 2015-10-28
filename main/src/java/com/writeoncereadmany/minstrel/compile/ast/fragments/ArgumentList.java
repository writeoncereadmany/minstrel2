package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ArgumentList implements AstNode
{
    private final Source source;
    public final List<Expression> expressions;

    public ArgumentList(Source source, List<Expression> expressions)
    {
        this.source = source;
        this.expressions = unmodifiableList(expressions);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitArgumentList(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

}
