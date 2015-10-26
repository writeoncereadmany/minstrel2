package com.writeoncereadmany.minstrel.compile.types.defintions;


import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;

public interface TypeDefinition
{
    Type getType(TypeChecker checker);

    TypeDefinition returnType(TypeChecker checker);

    TypeDefinition getMember(TypeChecker checker, String member);
}
