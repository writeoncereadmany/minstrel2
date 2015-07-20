package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.names.ScopeIndex;

public class Variable implements Expression
{
    private final Terminal name;
    private ScopeIndex index;

    public Variable(Terminal name)
    {
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitVariable(name);
    }

}
