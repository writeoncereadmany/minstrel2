package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

public class Interface implements Concern, TypeDefinition
{
    public final Map<String, TypeDefinition> members;

    public Interface(Map<String, TypeDefinition> members)
    {
        this.members = Collections.unmodifiableMap(members);
    }

    @Override
    public Type getType(TypeChecker checker)
    {
        return new Type(this);
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
}
