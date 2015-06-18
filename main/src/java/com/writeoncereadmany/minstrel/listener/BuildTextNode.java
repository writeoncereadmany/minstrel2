package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.ast.AstNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class BuildTextNode extends RuleProcessor
{
    private final Constructor constructor;

    public BuildTextNode(Class<? extends ParserRuleContext> contextType, Constructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addNodeToCurrent(constructor.construct(ctx.getText()));
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }


    @FunctionalInterface
    interface Constructor
    {
        AstNode construct(String text);
    }
}
