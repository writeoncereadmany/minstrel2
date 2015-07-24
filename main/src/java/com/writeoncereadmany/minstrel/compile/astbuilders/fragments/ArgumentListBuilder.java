package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class ArgumentListBuilder extends NodeSequenceBuilder<Expression, ArgumentList>
{
    public ArgumentListBuilder()
    {
        super(ArgumentList::new, Expression.class);
    }
}
