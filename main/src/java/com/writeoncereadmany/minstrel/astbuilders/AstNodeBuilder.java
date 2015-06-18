package com.writeoncereadmany.minstrel.astbuilders;

import com.writeoncereadmany.minstrel.ast.AstNode;

public interface AstNodeBuilder<T extends AstNode>
{
    T build();

    void addNode(AstNode node);

    void addTerminal(String text);
}
