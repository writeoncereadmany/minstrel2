package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
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
    public Type getType(TypeChecker checker)
    {
        Interface accessedObject = object.getType(checker).getConcern(Interface.class);

        TypeDefinition memberType = accessedObject != null ? accessedObject.getMember(member) : new UndefinedType("No such member");
        return memberType.getType(checker);
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
