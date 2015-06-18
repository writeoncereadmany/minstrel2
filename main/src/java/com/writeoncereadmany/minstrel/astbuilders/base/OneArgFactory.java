package com.writeoncereadmany.minstrel.astbuilders.base;

@FunctionalInterface
public interface OneArgFactory<R, A>
{
    R build(A arg);
}
