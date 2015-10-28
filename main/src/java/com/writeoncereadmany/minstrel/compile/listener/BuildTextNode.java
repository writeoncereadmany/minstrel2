package com.writeoncereadmany.minstrel.compile.listener;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import org.antlr.v4.runtime.ParserRuleContext;

import static com.writeoncereadmany.minstrel.compile.listener.ContextUtils.getColumn;
import static com.writeoncereadmany.minstrel.compile.listener.ContextUtils.getLine;

public class BuildTextNode extends RuleProcessor
{
    private final TerminalConstructor constructor;

    public BuildTextNode(Class<? extends ParserRuleContext> contextType, TerminalConstructor constructor)
    {
        super(contextType);
        this.constructor = constructor;
    }

    @Override
    public void onEnter(ParserRuleContext ctx, ASTBuilder builder)
    {
        builder.addNodeToCurrent(constructor.construct(Source.fromContext(ctx), new Terminal(ctx.getText(), getLine(ctx), getColumn(ctx))));
    }

    @Override
    public void onExit(ParserRuleContext ctx, ASTBuilder builder)
    {
    }
}
