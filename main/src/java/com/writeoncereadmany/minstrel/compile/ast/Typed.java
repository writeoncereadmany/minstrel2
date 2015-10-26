package com.writeoncereadmany.minstrel.compile.ast;

import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;

public interface Typed
{
    TypeDefinition type(TypeChecker checker);
}
