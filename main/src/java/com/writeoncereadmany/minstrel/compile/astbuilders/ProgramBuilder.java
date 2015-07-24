package com.writeoncereadmany.minstrel.compile.astbuilders;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.NodeSequenceBuilder;

public class ProgramBuilder extends NodeSequenceBuilder<Statement, Program>
{
    public ProgramBuilder()
    {
        super(Program::new, Statement.class);
    }
}
