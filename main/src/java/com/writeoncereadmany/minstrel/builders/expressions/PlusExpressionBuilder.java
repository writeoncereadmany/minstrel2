package com.writeoncereadmany.minstrel.builders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.PlusExpression;
import com.writeoncereadmany.minstrel.builders.base.TwoNodeBuilder;

public class PlusExpressionBuilder extends TwoNodeBuilder<Expression, Expression, PlusExpression>
{
    public PlusExpressionBuilder() {
        super(PlusExpression::new, Expression.class, Expression.class);
    }
}
