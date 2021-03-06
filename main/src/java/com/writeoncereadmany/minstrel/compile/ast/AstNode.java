package com.writeoncereadmany.minstrel.compile.ast;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public interface AstNode
{
    void visit(AstVisitor visitor);

    Source getSource();
}
