package com.writeoncereadmany.minstrel.types;


import com.writeoncereadmany.minstrel.names.ScopeIndex;

import java.util.function.Function;

public interface TypeDefinition
{
    Type getType(Function<ScopeIndex, Type> typeDefinitions);
}
