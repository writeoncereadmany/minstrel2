package com.writeoncereadmany.minstrel.builders;

import com.writeoncereadmany.minstrel.ast.Program;
import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.builders.base.NodeSequenceBuilder;

public class ProgramBuilder extends NodeSequenceBuilder<Statement, Program>
{
    public ProgramBuilder()
    {
        super(Program::new, Statement.class);
    }
}
