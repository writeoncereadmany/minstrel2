package com.writeoncereadmany.minstrel.astbuilders.statements;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Block;
import com.writeoncereadmany.minstrel.ast.Terminal;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

public class FunctionDeclarationBuilder implements AstNodeBuilder
{
    private Terminal name;
    private ParameterList parameters;
    private Block body;

    @Override
    public AstNode build() {
        return new FunctionDeclaration(name, parameters, body);
    }

    @Override
    public void addNode(AstNode node)
    {
        if(name == null)
        {
            throw new IllegalStateException("Expecting a name before being given a node: got " + node);
        }
        else if(parameters == null)
        {
            if(node instanceof ParameterList)
            {
                parameters = (ParameterList)node;
            }
            else
            {
                throw new IllegalArgumentException("Expecting a parameter list: got " + node);
            }
        }
        else if(body == null)
        {
            if(node instanceof Block)
            {
                body = (Block)node;
            }
            else
            {
                throw new IllegalArgumentException("Expecting a block: got " + node);
            }
        }
        else
        {
            throw new IllegalArgumentException("Expecting nothing more: got " + node);
        }
    }

    @Override
    public void addTerminal(Terminal text)
    {
        if(name == null)
        {
            name = text;
        }
        else
        {
            throw new IllegalArgumentException("Name already defined: got " + text);
        }
    }
}
