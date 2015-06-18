package com.writeoncereadmany.minstrel.walker;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by tom on 18/06/15.
 */
public class TerminalBuildRule extends BuildRule
{
    TerminalBuildRule(Class<? extends ParserRuleContext> contextType)
    {
        super(contextType);
    }

    @Override
    public void onEnter(ParserRuleContext ctx, MinstrelProgramBuilder builder)
    {
        builder.addTerminalToCurrent(ctx.getText());
    }

    @Override
    public void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder) {

    }

}
