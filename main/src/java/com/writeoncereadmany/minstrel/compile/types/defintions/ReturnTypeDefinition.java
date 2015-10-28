package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Nothing;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.concerns.IncoherentType;

public class ReturnTypeDefinition implements TypeDefinition
{
    private final TypeDefinition calledFunction;

    public ReturnTypeDefinition(TypeDefinition calledFunction)
    {
        this.calledFunction = calledFunction;
    }

    @Override
    public Type getType(TypeEngine checker)
    {
        Type calledType = calledFunction.getType(checker);
        if(calledType instanceof Nothing)
        {
            return Nothing.INSTANCE;
        }
        else
        {
            StructuralType structuralType = (StructuralType)calledType;
            FunctionType signature = structuralType.getConcern(FunctionType.class);
            return signature != null ? signature.returnType().getType(checker) : new StructuralType(new IncoherentType("Cannot call a non-function"));
        }
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

    @Override
    public String describe()
    {
        return "Return type of " + calledFunction;
    }
}
