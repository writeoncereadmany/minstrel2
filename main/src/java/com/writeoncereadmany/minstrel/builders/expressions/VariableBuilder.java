package com.writeoncereadmany.minstrel.builders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.builders.base.SingleTerminalBuilder;

public class VariableBuilder extends SingleTerminalBuilder<Variable>
{
    public VariableBuilder()
    {
        super(Variable::new);
    }
}
