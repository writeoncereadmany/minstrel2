package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class StringLiteral implements Expression
{
    public final Terminal value;

    public StringLiteral(Terminal value)
    {
        this.value = value;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitStringLiteral(this);
    }

}
