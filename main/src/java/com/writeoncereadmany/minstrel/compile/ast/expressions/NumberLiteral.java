package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class NumberLiteral implements Expression
{
    public final Terminal value;

    public NumberLiteral(Terminal value)
    {
        this.value = value;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitNumberLiteral(this);
    }

}
