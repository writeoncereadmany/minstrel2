package com.writeoncereadmany.minstrel.compile.astbuilders.base;

import com.writeoncereadmany.minstrel.compile.Source;

@FunctionalInterface
public interface OneArgFactory<R, A>
{
    R build(Source source, A arg);
}
