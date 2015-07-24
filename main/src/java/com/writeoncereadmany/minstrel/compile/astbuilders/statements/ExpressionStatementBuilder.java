package com.writeoncereadmany.minstrel.compile.astbuilders.statements;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.SingleNodeBuilder;

public class ExpressionStatementBuilder extends SingleNodeBuilder<Expression, ExpressionStatement>
{
    public ExpressionStatementBuilder()
    {
        super(ExpressionStatement::new, Expression.class);
    }
}
