package com.writeoncereadmany.minstrel.astbuilders.expressions;


import com.writeoncereadmany.minstrel.ast.Body;
import com.writeoncereadmany.minstrel.ast.expressions.Function;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.astbuilders.base.TwoNodeBuilder;

public class FunctionBuilder extends TwoNodeBuilder<ParameterList, Body, Function>
{
    public FunctionBuilder()
    {
        super(Function::new, ParameterList.class, Body.class);
    }
}
