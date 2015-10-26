package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;

public enum UndefinedType implements TypeDefinition
{
    INSTANCE;

    @Override
    public Type getType(TypeChecker checker)
    {
        return new Type();
    }

    @Override
    public TypeDefinition returnType(TypeChecker checker)
    {
        return this;
    }

    @Override
    public TypeDefinition getMember(TypeChecker checker, String member)
    {
        return this;
    }
}
