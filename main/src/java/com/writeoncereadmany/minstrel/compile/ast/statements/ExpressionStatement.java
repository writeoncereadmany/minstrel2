package com.writeoncereadmany.minstrel.compile.ast.statements;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class ExpressionStatement implements Statement
{
    public final Expression expression;

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
