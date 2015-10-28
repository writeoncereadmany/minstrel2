package com.writeoncereadmany.minstrel.compile;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;

public class Source
{
    public final String text;
    public final CodeLocation start;
    public final CodeLocation end;

    public Source(String text, CodeLocation start, CodeLocation end)
    {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public static Source fromContext(ParserRuleContext context)
    {
        Interval entireNode = Interval.of(context.start.getStartIndex(), context.stop.getStopIndex());
        return new Source(context.start.getInputStream().getText(entireNode),
                          CodeLocation.fromToken(context.getStart()),
                          CodeLocation.fromToken(context.getStop()));
    }
}
