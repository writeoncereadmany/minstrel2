package com.writeoncereadmany.minstrel.builders.base;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.builders.AstNodeBuilder;

public class SingleTerminalBuilder<T extends AstNode> implements AstNodeBuilder<T>
{
    private final OneArgFactory<T, String> factory;

    private String text;

    public SingleTerminalBuilder(OneArgFactory<T, String> factory)
    {
        this.factory = factory;
    }

    @Override
    public T build()
    {
        if(text != null)
        {
            return factory.build(text);
        }
        throw new IllegalStateException("Tried to build but no text has been set");
    }

    @Override
    public void addNode(AstNode node)
    {

    }

    @Override
    public void addTerminal(String text)
    {
        if(this.text == null)
        {
            this.text = text;
        }
        else
        {
            throw new IllegalStateException("Should contain exactly one terminal: attempting to add a second");
        }
    }
}
