package com.writeoncereadmany.minstrel.compile.astbuilders;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;

public interface AstNodeBuilder<T extends AstNode>
{
    T build();

    void addNode(AstNode node);

    void addTerminal(Terminal text);
}
