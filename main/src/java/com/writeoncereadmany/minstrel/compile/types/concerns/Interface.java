package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;

import java.util.Collections;
import java.util.Map;

public class Interface implements Concern, TypeDefinition
{
    public final Map<String, TypeDefinition> members;
    private final String name;

    public Interface(Map<String, TypeDefinition> members, String name)
    {
        this.name = name;
        this.members = Collections.unmodifiableMap(members);
    }

    @Override
    public Type getType(TypeEngine checker)
    {
        return new StructuralType(this);
    }

    @Override
    public TypeDefinition returnType()
    {
        return new UndefinedType("Cannot call an object");
    }

    @Override
    public TypeDefinition getMember(String member)
    {
        return members.getOrDefault(member, new UndefinedType("No such member"));
    }

    @Override
    public String describe() {
        return name;
    }
}
