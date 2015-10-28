package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;

import java.util.List;

import static com.writeoncereadmany.util.Joiner.joinWith;
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
    public Type getType(TypeEngine engine)
    {
        return new StructuralType(this);
    }

    @Override
    public TypeDefinition returnType()
    {
        return returnType;
    }

    @Override
    public TypeDefinition getMember(String member)
    {
        return new UndefinedType("No such member on a function");
    }

    @Override
    public String describe()
    {
        return "[" + argumentTypes.stream().map(TypeDefinition::describe).collect(joinWith(", ")) + "] -> " + returnType.describe();
    }
}
