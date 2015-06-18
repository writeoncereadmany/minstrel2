package com.writeoncereadmany.minstrel.walker;

import com.writeoncereadmany.minstrel.ast.AstNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class TextNodeBuildRule extends BuildRule
{
    private final Constructor constructor;

    public TextNodeBuildRule(Class<? extends ParserRuleContext> contextType, Constructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, MinstrelProgramBuilder builder)
    {
        builder.addNodeToCurrent(constructor.construct(ctx.getText()));
    }

    @Override
    public void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder)
    {
    }


    @FunctionalInterface
    interface Constructor
    {
        AstNode construct(String text);
    }
}
