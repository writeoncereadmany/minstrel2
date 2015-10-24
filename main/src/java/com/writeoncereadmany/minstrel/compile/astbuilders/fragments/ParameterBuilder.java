package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

public class ParameterBuilder implements AstNodeBuilder<Parameter>
{
    private TypeExpression type;
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
        if(type == null)
        {
            type = (TypeExpression) node;
        }
        else
        {
            throw new IllegalArgumentException("Cannot assign another node to a parameter");
        }
    }

    @Override
    public void addTerminal(Terminal terminal)
    {
        if(name == null)
        {
            name = terminal;
        }
        else
        {
            throw new IllegalArgumentException("Name already assigned to parameter");
        }
    }
}
