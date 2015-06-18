package com.writeoncereadmany.minstrel.walker;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Program;
import org.antlr.v4.runtime.ParserRuleContext;

public class RootNodeBuildRule extends NodeBuilderRule
{
    AstNode rootNode;

    public RootNodeBuildRule(Class<? extends ParserRuleContext> contextType, Constructor constructor) {
        super(contextType, constructor);
    }

    @Override
    public void onExit(ParserRuleContext ctx, MinstrelProgramBuilder builder) {
        Program program = (Program) builder.finishBuildingNode();
        builder.finish(program);
    }

    public AstNode getRootNode()
    {
        return rootNode;
    }
}
