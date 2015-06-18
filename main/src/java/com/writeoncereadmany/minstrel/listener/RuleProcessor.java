package com.writeoncereadmany.minstrel.listener;


import org.antlr.v4.runtime.ParserRuleContext;

public abstract class RuleProcessor
{
    private final Class<? extends ParserRuleContext> contextType;

    protected RuleProcessor(Class<? extends ParserRuleContext> contextType)
    {
        this.contextType = contextType;
    }

    abstract void onEnter(ParserRuleContext ctx, ASTBuilder builder);
    abstract void onExit(ParserRuleContext ctx, ASTBuilder builder);

    final Class<? extends ParserRuleContext> getContextType()
    {
        return this.contextType;
    }
}
