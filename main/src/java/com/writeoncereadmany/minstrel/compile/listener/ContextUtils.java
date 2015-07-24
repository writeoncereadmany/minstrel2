package com.writeoncereadmany.minstrel.compile.listener;

import org.antlr.v4.runtime.ParserRuleContext;

public class ContextUtils
{
    public static int getLine(ParserRuleContext ctx)
    {
        return ctx.getStart().getLine();
    }

    public static int getColumn(ParserRuleContext ctx)
    {
        return ctx.getStart().getCharPositionInLine();
    }
}
