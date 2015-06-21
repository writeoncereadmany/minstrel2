package com.writeoncereadmany.minstrel.astbuilders;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Terminal;

public interface AstNodeBuilder<T extends AstNode>
{
    T build();

    void addNode(AstNode node);

    void addTerminal(Terminal text);
}
