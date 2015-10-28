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
        final String text;
        final CodeLocation start;
        final CodeLocation stop;

        // all sorts of icky to deal with when we have empty nodes with no corresponding tokens :(
        if(context.start == null || context.stop == null)
        {
            text = context.getText();
            if(context.start != null)
            {
                start = CodeLocation.fromToken(context.start);
            }
            else
            {
                start = new CodeLocation(0, 0);
            }
            stop = start;
        }
        else
        {
            Interval entireNode = Interval.of(context.start.getStartIndex(), context.stop.getStopIndex());
            text = context.start.getInputStream().getText(entireNode);
            start = CodeLocation.fromToken(context.start);
            stop = CodeLocation.fromToken(context.stop);
        }

        return new Source(text, start, stop);
    }
}
