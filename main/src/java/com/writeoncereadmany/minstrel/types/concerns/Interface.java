package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.types.Type;

import java.util.Map;

public class Interface implements Concern
{
    private final Map<String, Type> members;

    public Interface(Map<String, Type> members)
    {
        this.members = members;
    }
}
