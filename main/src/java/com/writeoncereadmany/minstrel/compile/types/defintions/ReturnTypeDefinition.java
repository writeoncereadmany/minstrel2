package com.writeoncereadmany.minstrel.compile.types.defintions;

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
        FunctionType signature = calledType.getConcern(FunctionType.class);
        return signature != null ? signature.returnType().getType(checker) : new Type(new IncoherentType("Cannot call a non-function"));
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
}
