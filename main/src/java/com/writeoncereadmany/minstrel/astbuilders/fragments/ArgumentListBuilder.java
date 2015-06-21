package com.writeoncereadmany.minstrel.astbuilders.fragments;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.astbuilders.base.NodeSequenceBuilder;

public class ArgumentListBuilder extends NodeSequenceBuilder<Expression, ArgumentList>
{
    public ArgumentListBuilder()
    {
        super(ArgumentList::new, Expression.class);
    }
}
