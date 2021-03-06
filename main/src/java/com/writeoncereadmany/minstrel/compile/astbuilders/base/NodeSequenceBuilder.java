package com.writeoncereadmany.minstrel.compile.astbuilders.base;


import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class NodeSequenceBuilder<N extends AstNode, T extends AstNode> implements AstNodeBuilder<T>
{
    private final Source source;
    private final OneArgFactory<T, List<N>> factory;
    private final Class<N> nodeClass;
    private final List<N> elements = new ArrayList<>();

    public NodeSequenceBuilder(Source source, OneArgFactory<T, List<N>> factory, Class<N> nodeClass)
    {
        this.source = source;
        this.factory = factory;
        this.nodeClass = nodeClass;
    }

    @Override
    public T build() {
        return factory.build(source, elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addNode(AstNode node)
    {
        if(!nodeClass.isAssignableFrom(node.getClass()))
        {
            throw new IllegalArgumentException("Cannot assign " + node.getClass().getSimpleName() +
                                               " to a " + nodeClass.getSimpleName());
        }

        elements.add((N)node);
    }

    @Override
    public void addTerminal(Terminal text)
    {
        throw new IllegalArgumentException("Should not add terminals: expecting a sequence of nodes. Received " + text);
    }
}
