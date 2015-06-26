package com.writeoncereadmany.minstrel.astbuilders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.astbuilders.base.TwoNodeBuilder;

import static java.util.Collections.singletonList;

public class PlusExpressionBuilder extends TwoNodeBuilder<Expression, Expression, Expression>
{
    public PlusExpressionBuilder()
    {
        super(PlusExpressionBuilder::buildPlusExpression, Expression.class, Expression.class);
    }

    private static Expression buildPlusExpression(Expression augend, Expression addend)
    {
        MemberAccess plusFunction = new MemberAccess(augend, new Terminal("plus", -1, -1));
        return new FunctionCall(plusFunction, new ArgumentList(singletonList(addend)));
    }
}
