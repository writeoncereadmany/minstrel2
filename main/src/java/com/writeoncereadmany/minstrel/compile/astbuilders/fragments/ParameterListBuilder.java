package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class ParameterListBuilder extends NodeSequenceBuilder<Parameter, ParameterList>
{
    public ParameterListBuilder()
    {
        super(ParameterList::new, Parameter.class);
    }
}
