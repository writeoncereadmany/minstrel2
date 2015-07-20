package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;

public interface AstNode
{
    void visit(AstVisitor visitor);
}
