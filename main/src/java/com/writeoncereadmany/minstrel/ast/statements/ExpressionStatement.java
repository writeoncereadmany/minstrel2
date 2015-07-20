package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;

public class ExpressionStatement implements Statement
{
    private final Expression expression;

    public ExpressionStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitExpressionStatement(expression);
    }

}
