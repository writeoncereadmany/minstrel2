package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.names.NameResolver;

public interface AstNode
{
    void defineNames(NameResolver nameResolver);

    void resolveNames(NameResolver nameResolver);
}
