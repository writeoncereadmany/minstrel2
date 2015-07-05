package com.writeoncereadmany.minstrel.types.defintions;


import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;

import java.util.function.Function;

public interface TypeDefinition
{
    Type getType(Function<ScopeIndex, Type> typeDefinitions);
}
