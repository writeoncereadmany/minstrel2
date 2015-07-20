package com.writeoncereadmany.minstrel.astbuilders.expressions;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

import java.util.Map;

import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;

public class BinaryOperatorExpressionBuilder implements AstNodeBuilder<Expression>
{
    private static final Map<String, Terminal> methodNames = mapOf(entry("+", new Terminal("plus", -1, -1)));

    private Expression subject;
    private Expression object;
    private Terminal operator;

    @Override
    public Expression build()
    {
        Variable function = new Variable(lookupMethodName(operator));
        return new FunctionCall(function, new ArgumentList(asList(subject, object)));
    }

    private Terminal lookupMethodName(Terminal operator)
    {
        return methodNames.computeIfAbsent(operator.text, text ->
        {
            throw new IllegalArgumentException("No function found for " + text);
        });
    }

    @Override
    public void addNode(AstNode node)
    {
        if(subject == null)
        {
            subject = (Expression)node;
        }
        else
        {
            object = (Expression)node;
        }
    }

    @Override
    public void addTerminal(Terminal text)
    {
        if(subject != null && object == null)
        {
            this.operator = text;
        }
    }

}
