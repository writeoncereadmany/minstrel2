package com.writeoncereadmany.minstrel.builders.statements;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.builders.base.SingleNodeBuilder;

public class ExpressionStatementBuilder extends SingleNodeBuilder<Expression, ExpressionStatement>
{
    public ExpressionStatementBuilder()
    {
        super(ExpressionStatement::new, Expression.class);
    }
}
