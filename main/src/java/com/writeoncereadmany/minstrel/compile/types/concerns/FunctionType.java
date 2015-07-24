package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class FunctionType implements Concern
{
    public final List<TypeDefinition> argumentTypes;
    public final TypeDefinition returnType;

    public FunctionType(List<TypeDefinition> argumentTypes, TypeDefinition returnType)
    {
        this.argumentTypes = unmodifiableList(argumentTypes);
        this.returnType = returnType;
    }
}
