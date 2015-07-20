package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;

public class NumberLiteral implements Expression
{
    private final Terminal value;

    public NumberLiteral(Terminal value)
    {
        this.value = value;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitNumberLiteral(value);
    }

}
