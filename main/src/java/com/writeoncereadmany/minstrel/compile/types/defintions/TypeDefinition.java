package com.writeoncereadmany.minstrel.compile.types.defintions;


import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;

public interface TypeDefinition
{
    Type getType(TypeEngine checker);

    TypeDefinition returnType();

    TypeDefinition getMember(String member);

    String describe();
}
