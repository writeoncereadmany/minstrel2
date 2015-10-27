package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;

import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NegateExpressionBuilder implements AstNodeBuilder<Expression>
{
    private Expression argument;
    private Terminal terminal;

    @Override
    public Expression build()
    {
        MemberAccess negateMethod = new MemberAccess(argument, new Terminal("negate", terminal.line, terminal.column));
        return new FunctionCall(negateMethod, new ArgumentList(emptyList()));
    }

    @Override
    public void addNode(AstNode node)
    {
        argument = (Expression)node;
    }

    @Override
    public void addTerminal(Terminal text)
    {
        terminal = text;
    }
}
