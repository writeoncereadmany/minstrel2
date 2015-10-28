package com.writeoncereadmany.minstrel.compile.astbuilders.base;

import com.writeoncereadmany.minstrel.compile.Source;

@FunctionalInterface
public interface TwoArgFactory<R, A1, A2>
{
    R build(Source source, A1 arg1, A2 arg2);
}
