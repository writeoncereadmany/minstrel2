package com.writeoncereadmany.minstrel.compile.astbuilders.statements;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

public class VariableDeclarationBuilder implements AstNodeBuilder<VariableDeclaration>
{
    private TypeExpression type;
    private Terminal name;
    private Expression expression;

    @Override
    public VariableDeclaration build()
    {
        return new VariableDeclaration(type, name, expression);
    }

    @Override
    public void addNode(AstNode node)
    {
        if(type == null)
        {
            type = (TypeExpression)node;
        }
        else if(name != null)
        {
            expression = (Expression)node;
        }
        else
        {
            throw new IllegalStateException("Should only define expression for declaration value once type and name declared");
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
            throw new IllegalStateException("Name already defined: should not be adding more terminals. Terminal is " + terminal);
        }
    }

}
