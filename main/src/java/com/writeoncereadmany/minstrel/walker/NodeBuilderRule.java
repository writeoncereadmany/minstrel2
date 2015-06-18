package com.writeoncereadmany.minstrel.walker;

import com.writeoncereadmany.minstrel.builders.AstNodeBuilder;
import org.antlr.v4.runtime.ParserRuleContext;

public class NodeBuilderRule extends BuildRule {

    private final Constructor constructor;

    public NodeBuilderRule(Class<? extends ParserRuleContext> contextType, Constructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, MinstrelProgramBuilder builder)
    {
        builder.startBuildingNode(constructor.construct());
    }

    @Override
    public void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder)
    {
        builder.addNodeToCurrent(builder.finishBuildingNode());
    }


    @FunctionalInterface
    interface Constructor
    {
        AstNodeBuilder construct();
    }
}
