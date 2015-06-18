package com.writeoncereadmany.minstrel.builders.base;

@FunctionalInterface
public interface OneArgFactory<R, A>
{
    R build(A arg);
}
