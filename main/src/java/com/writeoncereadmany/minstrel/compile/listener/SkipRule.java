package com.writeoncereadmany.minstrel.compile.listener;

import org.antlr.v4.runtime.ParserRuleContext;

public class SkipRule extends RuleProcessor
{
    public static final RuleProcessor INSTANCE = new SkipRule();

    private SkipRule() {
        super(null);
    }

    @Override
    void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
    }

    @Override
    void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }
}
