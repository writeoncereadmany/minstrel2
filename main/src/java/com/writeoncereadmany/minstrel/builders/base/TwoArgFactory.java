package com.writeoncereadmany.minstrel.builders.base;

@FunctionalInterface
public interface TwoArgFactory<R, A1, A2>
{
    R build(A1 arg1, A2 arg2);
}
