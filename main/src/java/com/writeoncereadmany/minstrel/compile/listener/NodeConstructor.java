package com.writeoncereadmany.minstrel.compile.listener;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

@FunctionalInterface
interface NodeConstructor
{
    AstNodeBuilder construct(Source source);
}
