package com.writeoncereadmany.minstrel.astbuilders;

import com.writeoncereadmany.minstrel.ast.Block;
import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.astbuilders.base.NodeSequenceBuilder;

public class BlockBuilder extends NodeSequenceBuilder<Statement, Block>
{
    public BlockBuilder()
    {
        super(Block::new, Statement.class);
    }
}
