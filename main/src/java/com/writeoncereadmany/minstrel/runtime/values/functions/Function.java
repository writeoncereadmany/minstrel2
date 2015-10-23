package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.values.Value;

public abstract class Function implements Value
{
    @Override
    public final Value get(String name)
    {
        throw new UnsupportedOperationException("Functions cannot have members");
    }
}
