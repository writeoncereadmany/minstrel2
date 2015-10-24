package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class TypeListBuilder extends NodeSequenceBuilder<TypeExpression, TypeList>
{
    public TypeListBuilder()
    {
        super(TypeList::new, TypeExpression.class);
    }
}
