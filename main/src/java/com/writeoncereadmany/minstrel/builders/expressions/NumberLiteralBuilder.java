package com.writeoncereadmany.minstrel.builders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.NumberLiteral;
import com.writeoncereadmany.minstrel.builders.base.SingleTerminalBuilder;

public class NumberLiteralBuilder extends SingleTerminalBuilder<NumberLiteral>
{
    public NumberLiteralBuilder()
    {
        super(NumberLiteral::new);
    }
}
