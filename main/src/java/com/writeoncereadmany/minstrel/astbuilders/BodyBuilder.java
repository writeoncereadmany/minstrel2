package com.writeoncereadmany.minstrel.astbuilders;

import com.writeoncereadmany.minstrel.ast.Body;
import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.astbuilders.base.NodeSequenceBuilder;

public class BodyBuilder extends NodeSequenceBuilder<Statement, Body>
{
    public BodyBuilder()
    {
        super(Body::new, Statement.class);
        int a = 0;
    }
}