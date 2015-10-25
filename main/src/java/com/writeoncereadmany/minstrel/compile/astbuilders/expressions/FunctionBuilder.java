package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;


import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionBuilder extends TwoNodeBuilder<ParameterList, Body, FunctionExpression>
{
    public FunctionBuilder()
    {
        super(FunctionExpression::new, ParameterList.class, Body.class);
    }
}
