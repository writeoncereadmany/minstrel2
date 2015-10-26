package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.concerns.IncoherentType;

public class UndefinedType implements TypeDefinition
{
    private final String reason;

    public UndefinedType(String reason)
    {
        this.reason = reason;
    }

    @Override
    public Type getType(TypeChecker checker)
    {
        return new Type(new IncoherentType(reason));
    }

    @Override
    public TypeDefinition returnType()
    {
        return this;
    }

    @Override
    public TypeDefinition getMember(String member)
    {
        return this;
    }
}
