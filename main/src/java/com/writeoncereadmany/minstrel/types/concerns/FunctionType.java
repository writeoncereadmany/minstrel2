package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.types.Type;

import java.util.List;

public class FunctionType implements Concern
{
    private final List<Type> argumentTypes;
    private final Type returnType;

    public FunctionType(List<Type> argumentTypes, Type returnType)
    {
        this.argumentTypes = argumentTypes;
        this.returnType = returnType;
    }
}
