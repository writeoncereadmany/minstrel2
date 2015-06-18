package com.writeoncereadmany.minstrel.walker;


import org.antlr.v4.runtime.ParserRuleContext;

public abstract class BuildRule
{
    private final Class<? extends ParserRuleContext> contextType;

    protected BuildRule(Class<? extends ParserRuleContext> contextType)
    {
        this.contextType = contextType;
    }

    abstract void onEnter(ParserRuleContext ctx, MinstrelProgramBuilder builder);
    abstract void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder);

    final Class<? extends ParserRuleContext> getContextType()
    {
        return this.contextType;
    }
}
