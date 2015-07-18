package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.names.NameResolver;

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

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        // name-agnostic
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        // name-agnostic
    }
}
