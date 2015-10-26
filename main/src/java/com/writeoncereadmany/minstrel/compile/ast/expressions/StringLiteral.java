package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
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

    @Override
    public TypeDefinition type(TypeChecker checker)
    {
        return new ConcreteTypeDefinition(Builtins.STRING_TYPE.scopeIndex());
    }
}
