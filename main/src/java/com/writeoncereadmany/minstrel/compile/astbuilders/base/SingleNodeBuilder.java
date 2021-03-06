package com.writeoncereadmany.minstrel.compile.astbuilders.base;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

public class SingleNodeBuilder<N extends AstNode, T extends AstNode> implements AstNodeBuilder<T>
{
    private final Source source;
    private final OneArgFactory<T, N> factory;
    private final Class<N> nodeClass;
    private N node;

    public SingleNodeBuilder(Source source, OneArgFactory<T, N> factory, Class<N> nodeClass)
    {
        this.source = source;
        this.factory = factory;
        this.nodeClass = nodeClass;
    }

    @Override
    public T build()
    {
        if(node != null)
        {
            return factory.build(source, node);
        }
        throw new IllegalStateException("Should contain a node, but attempted to build with no node");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addNode(AstNode node)
    {
        if(this.node == null)
        {
            if(!nodeClass.isAssignableFrom(node.getClass()))
            {
                throw new IllegalArgumentException("Cannot assign " + node.getClass().getSimpleName() +
                                                   " to a " + nodeClass.getSimpleName());
            }
            this.node = (N) node;
        }
        else
        {
            throw new IllegalStateException("Should only contain one node, but a second one has been added");
        }
    }

    @Override
    public void addTerminal(Terminal text)
    {
        throw new IllegalArgumentException("Should not add terminals: expecting a single node");
    }
}
