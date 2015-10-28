package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;


import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionBuilder extends TwoNodeBuilder<ParameterList, Body, FunctionExpression>
{
    public FunctionBuilder(Source source)
    {
        super(source, FunctionExpression::new, ParameterList.class, Body.class);
    }
}
