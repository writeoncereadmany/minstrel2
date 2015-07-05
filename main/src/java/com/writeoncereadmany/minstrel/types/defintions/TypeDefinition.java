package com.writeoncereadmany.minstrel.types.defintions;


import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;

public interface TypeDefinition
{
    Type getType(TypeChecker engine);
}
