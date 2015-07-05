package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.types.Type;

import java.util.Collections;
import java.util.Map;

public class Interface implements Concern
{
    public final Map<String, Type> members;

    public Interface(Map<String, Type> members)
    {
        this.members = Collections.unmodifiableMap(members);
    }
}
