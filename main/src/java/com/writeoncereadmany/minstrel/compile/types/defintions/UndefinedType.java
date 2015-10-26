package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;

public enum UndefinedType implements TypeDefinition
{
    INSTANCE;

    @Override
    public Type getType(TypeChecker checker)
    {
        // I don't know what to do if we try to get the type of something undefined.
        // Probably an error: this will no doubt make sense later...?
        return new Type();
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
