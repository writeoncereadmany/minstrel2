package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Nothing;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.concerns.Interface;

public class MemberTypeDefinition implements TypeDefinition {

    private final TypeDefinition object;
    private final String member;

    public MemberTypeDefinition(final TypeDefinition object, final String member)
    {
        this.object = object;
        this.member = member;
    }

    @Override
    public Type getType(TypeEngine checker)
    {
        Type type = object.getType(checker);
        if(type instanceof Nothing)
        {
            return Nothing.INSTANCE;
        }
        else
        {
            Interface accessedObject = ((StructuralType)type).getConcern(Interface.class);

            TypeDefinition memberType = accessedObject != null ? accessedObject.getMember(member) : new UndefinedType("No such member");
            return memberType.getType(checker);
        }
    }

    @Override
    public TypeDefinition returnType()
    {
        return new ReturnTypeDefinition(this);
    }

    @Override
    public TypeDefinition getMember(String member)
    {
        return new MemberTypeDefinition(this, member);
    }
}
