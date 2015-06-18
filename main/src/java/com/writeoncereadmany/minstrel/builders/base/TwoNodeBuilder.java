package com.writeoncereadmany.minstrel.builders.base;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.builders.AstNodeBuilder;

public class TwoNodeBuilder<N1 extends AstNode, N2 extends AstNode, T extends AstNode> implements AstNodeBuilder<T>
{
    private final TwoArgFactory<T, N1, N2> factory;
    private final Class<N1> node1Class;
    private final Class<N2> node2Class;
    private N1 node1;
    private N2 node2;

    public TwoNodeBuilder(TwoArgFactory<T, N1, N2> factory, Class<N1> node1Class, Class<N2> node2Class)
    {
        this.factory = factory;
        this.node1Class = node1Class;
        this.node2Class = node2Class;
    }

    @Override
    public T build()
    {
        if(node1 != null && node2 != null)
        {
            return factory.build(node1, node2);
        }
        throw new IllegalStateException("Should contain two node, but attempted to build without both");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addNode(AstNode node)
    {
        if(this.node1 == null)
        {
            if(!node1Class.isAssignableFrom(node.getClass()))
            {
                throw new IllegalArgumentException("Cannot assign " + node.getClass().getSimpleName() +
                                                   " to a " + node1Class.getSimpleName());
            }
            this.node1 = (N1) node;
        }
        else if(this.node2 == null)
        {
            if(!node2Class.isAssignableFrom(node.getClass()))
            {
                throw new IllegalArgumentException("Cannot assign " + node.getClass().getSimpleName() +
                                                   " to a " + node2Class.getSimpleName());
            }
            this.node2 = (N2) node;
        }
        else
        {
            throw new IllegalStateException("Should only contain two nodes, but a third one has been added");
        }
    }

    @Override
    public void addTerminal(String text)
    {
        throw new IllegalArgumentException("Should not add terminals: expecting only two nodes");
    }
}
