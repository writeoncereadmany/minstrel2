package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

public class ParameterBuilder implements AstNodeBuilder<Parameter>
{
    private Terminal type;
    private Terminal name;

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
    public void addTerminal(Terminal terminal)
    {
        if(type == null)
        {
            type = terminal;
        }
        else if(name == null)
        {
            name = terminal;
        }
        else
        {
            throw new IllegalArgumentException("Two terminals already assigned to parameter");
        }
    }
}
