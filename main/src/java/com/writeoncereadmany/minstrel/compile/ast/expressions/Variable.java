package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class Variable implements Expression
{
    public final Terminal name;

    public Variable(Terminal name)
    {
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitVariable(this);
    }

    @Override
    public TypeDefinition type()
    {
        return null;
    }
}
