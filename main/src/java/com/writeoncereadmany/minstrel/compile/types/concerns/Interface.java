package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;

import java.util.Collections;
import java.util.Map;

public class Interface implements Concern
{
    public final Map<String, TypeDefinition> members;

    public Interface(Map<String, TypeDefinition> members)
    {
        this.members = Collections.unmodifiableMap(members);
    }
}
