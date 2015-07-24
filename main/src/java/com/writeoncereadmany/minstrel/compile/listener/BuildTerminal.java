package com.writeoncereadmany.minstrel.compile.listener;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import org.antlr.v4.runtime.ParserRuleContext;

import static com.writeoncereadmany.minstrel.compile.listener.ContextUtils.getColumn;
import static com.writeoncereadmany.minstrel.compile.listener.ContextUtils.getLine;

public class BuildTerminal extends RuleProcessor
{
    BuildTerminal(Class<? extends ParserRuleContext> contextType)
    {
        super(contextType);
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addTerminalToCurrent(new Terminal(ctx.getText(), getLine(ctx), getColumn(ctx)));
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }

}
