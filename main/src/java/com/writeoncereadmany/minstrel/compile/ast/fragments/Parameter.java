package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class Parameter implements AstNode
{
    public final TypeExpression type;
    public final Terminal name;

    public Parameter(TypeExpression type, Terminal name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameter(this);
    }

}
