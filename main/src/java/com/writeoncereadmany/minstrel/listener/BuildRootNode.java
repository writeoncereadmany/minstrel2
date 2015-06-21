package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Program;
import org.antlr.v4.runtime.ParserRuleContext;

public class BuildRootNode extends BuildCompoundNode
{
    AstNode rootNode;

    public BuildRootNode(Class<? extends ParserRuleContext> contextType, Constructor constructor)
    {
        super(contextType, constructor);
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
        Program program = (Program) builder.finishBuildingNode();
        builder.finish(program);
    }

    public AstNode getRootNode()
    {
        return rootNode;
    }
}
