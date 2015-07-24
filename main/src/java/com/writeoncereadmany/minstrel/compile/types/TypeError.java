package com.writeoncereadmany.minstrel.compile.types;

public class TypeError
{
    private final String reason;

    public TypeError(String reason)
    {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }
}
