package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class Parameter implements AstNode
{
    private final Source source;
    public final TypeExpression type;
    public final Terminal name;

    public Parameter(Source source, TypeExpression type, Terminal name)
    {
        this.source = source;
        this.type = type;
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameter(this);
    }

    @Override
    public Source getSource()
    {
        return source;
    }

}
