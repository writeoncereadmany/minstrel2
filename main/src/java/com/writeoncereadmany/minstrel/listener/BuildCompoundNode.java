package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;
import org.antlr.v4.runtime.ParserRuleContext;

public class BuildCompoundNode extends RuleProcessor {

    private final Constructor constructor;

    public BuildCompoundNode(Class<? extends ParserRuleContext> contextType, Constructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.startBuildingNode(constructor.construct());
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addNodeToCurrent(builder.finishBuildingNode());
    }


    @FunctionalInterface
    interface Constructor
    {
        AstNodeBuilder construct();
    }
}
