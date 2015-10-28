package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class ParameterListBuilder extends NodeSequenceBuilder<Parameter, ParameterList>
{
    public ParameterListBuilder(Source source)
    {
        super(source, ParameterList::new, Parameter.class);
    }
}
