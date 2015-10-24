package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class NamedType implements TypeExpression
{
    private final Terminal typeName;

    public NamedType(Terminal typeName)
    {
        this.typeName = typeName;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitNamedType(typeName);
    }
}
