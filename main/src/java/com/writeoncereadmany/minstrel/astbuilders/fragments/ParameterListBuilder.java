package com.writeoncereadmany.minstrel.astbuilders.fragments;

import com.writeoncereadmany.minstrel.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.astbuilders.base.NodeSequenceBuilder;

public class ParameterListBuilder extends NodeSequenceBuilder<Parameter, ParameterList>
{
    public ParameterListBuilder()
    {
        super(ParameterList::new, Parameter.class);
    }
}
