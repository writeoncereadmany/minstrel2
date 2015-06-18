package com.writeoncereadmany.minstrel.builders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.builders.base.TwoNodeBuilder;

public class FunctionCallBuilder extends TwoNodeBuilder<Expression, ArgumentList, FunctionCall>
{

    public FunctionCallBuilder()
    {
        super(FunctionCall::new, Expression.class, ArgumentList.class);
    }
}
