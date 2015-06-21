package com.writeoncereadmany.minstrel.listener;

import org.antlr.v4.runtime.ParserRuleContext;

public class BuildTerminal extends RuleProcessor
{
    BuildTerminal(Class<? extends ParserRuleContext> contextType)
    {
        super(contextType);
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addTerminalToCurrent(ctx.getText());
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }

}