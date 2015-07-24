package com.writeoncereadmany.minstrel.compile.astbuilders.statements;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

public class FunctionDeclarationBuilder implements AstNodeBuilder
{
    private Terminal name;
    private Function function;

    @Override
    public AstNode build() {
        return new FunctionDeclaration(name, function);
    }

    @Override
    public void addNode(AstNode node)
    {
        if(name == null)
        {
            throw new IllegalStateException("Expecting a name before being given a node: got " + node);
        }
        else if(function == null)
        {
            if(node instanceof Function)
            {
                function = (Function)node;
            }
            else
            {
                throw new IllegalArgumentException("Expecting a function: got " + node);
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
