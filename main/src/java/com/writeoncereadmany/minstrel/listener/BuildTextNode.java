package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import org.antlr.v4.runtime.ParserRuleContext;

import static com.writeoncereadmany.minstrel.listener.ContextUtils.getColumn;
import static com.writeoncereadmany.minstrel.listener.ContextUtils.getLine;

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
        builder.addNodeToCurrent(constructor.construct(new Terminal(ctx.getText(), getLine(ctx), getColumn(ctx))));
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }


    @FunctionalInterface
    interface Constructor
    {
        AstNode construct(Terminal terminal);
    }
}
