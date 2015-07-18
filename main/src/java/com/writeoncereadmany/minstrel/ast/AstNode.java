package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.names.NameResolver;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;

public interface AstNode
{
    void visit(AstVisitor visitor);

    void defineNames(NameResolver nameResolver);

    void resolveNames(NameResolver nameResolver);
}
