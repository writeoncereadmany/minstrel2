package com.writeoncereadmany.minstrel.builders.expressions;

import com.writeoncereadmany.minstrel.ast.expressions.StringLiteral;
import com.writeoncereadmany.minstrel.builders.base.SingleTerminalBuilder;

public class StringLiteralBuilder extends SingleTerminalBuilder<StringLiteral>
{
    public StringLiteralBuilder()
    {
        super(StringLiteral::new);
    }
}
