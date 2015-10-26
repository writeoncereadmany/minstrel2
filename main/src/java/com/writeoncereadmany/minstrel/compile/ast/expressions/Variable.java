package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
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
    public TypeDefinition type(TypeChecker checker)
    {
        // come back to this one: we'll need to look it up once we've done type definitions
        return UndefinedType.INSTANCE;
    }
}
