package com.writeoncereadmany.minstrel.compile.astbuilders.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class BodyBuilder extends NodeSequenceBuilder<Statement, Body>
{
    public BodyBuilder(Source source)
    {
        super(source, Body::new, Statement.class);
    }
}
