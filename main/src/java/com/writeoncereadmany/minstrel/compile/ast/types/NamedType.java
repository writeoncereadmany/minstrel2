package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class NamedType implements TypeExpression
{
    public final Terminal typeName;

    public NamedType(Terminal typeName)
    {
        this.typeName = typeName;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitNamedType(typeName);
    }

    @Override
    public TypeDefinition lookupType(NameResolver nameResolver)
    {
        return new ConcreteTypeDefinition(nameResolver.lookup(typeName, Kind.TYPE));
    }
}
