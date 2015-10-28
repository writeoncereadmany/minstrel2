package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;
import com.writeoncereadmany.util.Hope;

public class Variable implements Expression
{
    public final Terminal name;
    private final Hope<Typed> type = new Hope<>();
    private final Source source;

    public Variable(Source source, Terminal name)
    {
        this.source = source;
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitVariable(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return type.get().type();
    }

    public void setType(Typed type)
    {
        this.type.set(type);
    }
}
