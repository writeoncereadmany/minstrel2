package com.writeoncereadmany.minstrel.compile.listener;

import com.writeoncereadmany.minstrel.compile.Source;
import org.antlr.v4.runtime.ParserRuleContext;

public class BuildCompoundNode extends RuleProcessor {

    private final NodeConstructor constructor;

    public BuildCompoundNode(Class<? extends ParserRuleContext> contextType, NodeConstructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.startBuildingNode(constructor.construct(Source.fromContext(ctx)));
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addNodeToCurrent(builder.finishBuildingNode());
    }


}
