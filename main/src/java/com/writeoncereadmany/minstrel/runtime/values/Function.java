package com.writeoncereadmany.minstrel.runtime.values;

public abstract class Function implements Value
{
    @Override
    public final Value get(String name)
    {
        throw new UnsupportedOperationException("Functions cannot have members");
    }
}
