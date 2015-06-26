package com.writeoncereadmany.minstrel.astbuilders.expressions;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

public class MemberAccessBuilder implements AstNodeBuilder<MemberAccess> {

    private Expression expression;
    private Terminal memberName;

    @Override
    public MemberAccess build()
    {
        return new MemberAccess(expression, memberName);
    }

    @Override
    public void addNode(AstNode node)
    {
        if(expression == null)
        {
            expression = (Expression)node;
        }
        else
        {
            throw new IllegalArgumentException("Not expecting any more nodes: got " + node);
        }
    }

    @Override
    public void addTerminal(Terminal text)
    {
        if(expression != null && memberName == null)
        {
            memberName = text;
        }
        else
        {
            throw new IllegalArgumentException("Not expecting a terminal: got " + text);
        }
    }
}
