package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class NamedType implements TypeExpression
{
    public final Terminal name;

    public NamedType(Terminal name)
    {
        this.name = name;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitNamedType(this);
    }

    @Override
    public TypeDefinition lookupType(NameResolver nameResolver)
    {
        return new ConcreteTypeDefinition(nameResolver.lookup(name, Kind.TYPE));
    }
}
