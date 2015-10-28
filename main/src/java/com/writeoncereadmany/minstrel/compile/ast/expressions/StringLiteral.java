package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class StringLiteral implements Expression
{
    private final Source source;
    public final Terminal value;

    public StringLiteral(Source source, Terminal value)
    {
        this.source = source;
        this.value = value;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitStringLiteral(this);
    }

    @Override
    public Source getSource()
    {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return ConcreteTypeDefinition.fromTerminal(Builtins.STRING_TYPE);
    }
}
