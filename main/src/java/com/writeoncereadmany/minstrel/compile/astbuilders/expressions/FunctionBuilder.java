package com.writeoncereadmany.minstrel.compile.astbuilders.expressions;


import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionBuilder extends TwoNodeBuilder<ParameterList, Body, Function>
{
    public FunctionBuilder()
    {
        super(Function::new, ParameterList.class, Body.class);
    }
}
