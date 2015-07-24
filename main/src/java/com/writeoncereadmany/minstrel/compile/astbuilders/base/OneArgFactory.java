package com.writeoncereadmany.minstrel.compile.astbuilders.base;

@FunctionalInterface
public interface OneArgFactory<R, A>
{
    R build(A arg);
}
