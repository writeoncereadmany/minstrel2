package com.writeoncereadmany.minstrel.walker;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by tom on 18/06/15.
 */
public class NoOpRule extends BuildRule
{
    public static final BuildRule INSTANCE = new NoOpRule();

    private NoOpRule() {
        super(null);
    }

    @Override
    void onEnter(ParserRuleContext ctx, MinstrelProgramBuilder builder) {

    }

    @Override
    void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder) {

    }
}
