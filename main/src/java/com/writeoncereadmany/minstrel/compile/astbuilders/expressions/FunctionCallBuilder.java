package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionCallBuilder extends TwoNodeBuilder<Expression, ArgumentList, FunctionCall>
{
    public FunctionCallBuilder(Source source)
    {
        super(source, FunctionCall::new, Expression.class, ArgumentList.class);
    }
}
