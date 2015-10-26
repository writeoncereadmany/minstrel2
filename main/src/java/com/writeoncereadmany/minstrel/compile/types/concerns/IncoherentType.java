package com.writeoncereadmany.minstrel.compile.types.concerns;

public class IncoherentType implements Concern
{
    public final String reason;

    public IncoherentType(String reason)
    {
        this.reason = reason;
    }
}
