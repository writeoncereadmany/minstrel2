package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;

public class ReturnTypeDefinition implements TypeDefinition
{
    private final TypeDefinition calledFunction;

    public ReturnTypeDefinition(TypeDefinition calledFunction)
    {
        this.calledFunction = calledFunction;
    }

    @Override
    public Type getType(TypeChecker checker)
    {
        Type calledType = calledFunction.getType(checker);
        FunctionType signature = calledType.getConcern(FunctionType.class);
        return signature.returnType().getType(checker);
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
