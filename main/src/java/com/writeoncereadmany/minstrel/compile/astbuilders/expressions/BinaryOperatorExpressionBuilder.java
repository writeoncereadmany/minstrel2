package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.astbuilders.AstNodeBuilder;

import java.util.Map;

import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Collections.singletonList;

public class BinaryOperatorExpressionBuilder implements AstNodeBuilder<Expression>
{
    private static final Map<String, String> methodNames = mapOf(entry("+", "plus"),
                                                                   entry("-", "minus"),
                                                                   entry("*", "multipliedBy"),
                                                                   entry("/", "dividedBy"));

    private Expression subject;
    private Expression object;
    private Terminal operator;
    private final Source source;

    public BinaryOperatorExpressionBuilder(Source source)
    {
        this.source = source;
    }

    @Override
    public Expression build()
    {
        String methodName = lookupMethodName(operator);
        MemberAccess method = new MemberAccess(subject.getSource(), subject, new Terminal(methodName, operator.line, operator.column));
        return new FunctionCall(source, method, new ArgumentList(object.getSource(), singletonList(object)));
    }

    private String lookupMethodName(Terminal operator)
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
