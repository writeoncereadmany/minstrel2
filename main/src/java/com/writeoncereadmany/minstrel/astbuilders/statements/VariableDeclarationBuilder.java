package com.writeoncereadmany.minstrel.astbuilders.statements;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

public class VariableDeclarationBuilder implements AstNodeBuilder<VariableDeclaration>
{
    private String type;
    private String name;
    private Expression expression;

    @Override
    public VariableDeclaration build()
    {
        return new VariableDeclaration(type, name, expression);
    }

    @Override
    public void addNode(AstNode node)
    {
        if(type != null && name != null)
        {
            expression = (Expression)node;
        }
        else
        {
            throw new IllegalStateException("Should only define expression for declaration value once type and name declared");
        }
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
            throw new IllegalStateException("Type and name already defined: should not be adding more terminals. Text is " + text);
        }
    }

}
