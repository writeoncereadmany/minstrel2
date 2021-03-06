package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

import static java.util.Collections.emptyList;

public class NegateExpressionBuilder implements AstNodeBuilder<Expression>
{
    private final Source source;
    private Expression argument;
    private Terminal terminal;

    public NegateExpressionBuilder(Source source)
    {
        this.source = source;
    }

    @Override
    public Expression build()
    {
        MemberAccess negateMethod = new MemberAccess(argument.getSource(), argument, new Terminal("negate", terminal.line, terminal.column));
        return new FunctionCall(source, negateMethod, new ArgumentList(source, emptyList()));
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
