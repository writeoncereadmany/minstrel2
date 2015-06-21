package com.writeoncereadmany.minstrel.astbuilders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.PlusExpression;
import com.writeoncereadmany.minstrel.astbuilders.base.TwoNodeBuilder;

public class PlusExpressionBuilder extends TwoNodeBuilder<Expression, Expression, PlusExpression>
{
    public PlusExpressionBuilder()
    {
        super(PlusExpression::new, Expression.class, Expression.class);
    }
}
