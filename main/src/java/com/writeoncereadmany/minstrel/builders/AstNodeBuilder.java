package com.writeoncereadmany.minstrel.builders;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.statements.DeclarationStatement;

public interface AstNodeBuilder<T extends AstNode>
{
    T build();

    void addNode(AstNode node);

    void addTerminal(String text);
}
