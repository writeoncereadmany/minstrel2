package com.writeoncereadmany.minstrel.astbuilders.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

public class ParameterBuilder implements AstNodeBuilder<Parameter>
{
    private String type;
    private String name;

    @Override
    public Parameter build()
    {
        if(type == null || name == null)
        {
            throw new IllegalArgumentException("Parameter not fully defined");
        }
        return new Parameter(type, name);
    }

    @Override
    public void addNode(AstNode node)
    {
        throw new IllegalArgumentException("Cannot assign a node to a parameter");
    }

    @Override
    public void addTerminal(String text)
    {
        if(type == null)
        {
            type = text;
        }
        else if(name == null)
        {
            name = text;
        }
        else
        {
            throw new IllegalArgumentException("Two terminals already assigned to parameter");
        }
    }
}
