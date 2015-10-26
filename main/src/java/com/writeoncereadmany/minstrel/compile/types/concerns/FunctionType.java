package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class FunctionType implements Concern, TypeDefinition
{
    public final List<TypeDefinition> argumentTypes;
    public final TypeDefinition returnType;

    public FunctionType(List<TypeDefinition> argumentTypes, TypeDefinition returnType)
    {
        this.argumentTypes = unmodifiableList(argumentTypes);
        this.returnType = returnType;
    }

    @Override
    public Type getType(TypeChecker engine)
    {
        return new Type(this);
    }

    @Override
    public TypeDefinition returnType(TypeChecker checker)
    {
        return returnType;
    }

    @Override
    public TypeDefinition getMember(TypeChecker checker, String member)
    {
        return UndefinedType.INSTANCE;
    }
}
