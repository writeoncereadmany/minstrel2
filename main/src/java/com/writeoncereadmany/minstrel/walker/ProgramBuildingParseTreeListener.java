package com.writeoncereadmany.minstrel.walker;

import com.writeoncereadmany.minstrel.ast.expressions.NumberLiteral;
import com.writeoncereadmany.minstrel.ast.expressions.StringLiteral;
import com.writeoncereadmany.minstrel.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.builders.ProgramBuilder;
import com.writeoncereadmany.minstrel.builders.expressions.FunctionCallBuilder;
import com.writeoncereadmany.minstrel.builders.expressions.PlusExpressionBuilder;
import com.writeoncereadmany.minstrel.builders.fragments.ArgumentListBuilder;
import com.writeoncereadmany.minstrel.builders.statements.DeclarationStatementBuilder;
import com.writeoncereadmany.minstrel.builders.statements.ExpressionStatementBuilder;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelBaseListener;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ProgramBuildingParseTreeListener extends MinstrelBaseListener
{
    private final MinstrelProgramBuilder builder;

    public ProgramBuildingParseTreeListener(MinstrelProgramBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public void enterEveryRule(@NotNull ParserRuleContext ctx)
    {
        buildRules.getOrDefault(ctx.getClass(), NoOpRule.INSTANCE)
                  .onEnter(ctx, builder);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx)
    {
        buildRules.getOrDefault(ctx.getClass(), NoOpRule.INSTANCE)
                  .onExit(ctx, builder);
    }

    private static List<BuildRule> allRules = asList(
            new RootNodeBuildRule(MinstrelParser.ProgramContext.class, ProgramBuilder::new),

            new NodeBuilderRule(MinstrelParser.Declaration_statementContext.class, DeclarationStatementBuilder::new),
            new NodeBuilderRule(MinstrelParser.Expression_statementContext.class, ExpressionStatementBuilder::new),
            new NodeBuilderRule(MinstrelParser.Function_callContext.class, FunctionCallBuilder::new),
            new NodeBuilderRule(MinstrelParser.Plus_expressionContext.class, PlusExpressionBuilder::new),
            new NodeBuilderRule(MinstrelParser.Argument_listContext.class, ArgumentListBuilder::new),

            new TextNodeBuildRule(MinstrelParser.Number_literalContext.class, NumberLiteral::new),
            new TextNodeBuildRule(MinstrelParser.String_literalContext.class, StringLiteral::new),
            new TextNodeBuildRule(MinstrelParser.VariableContext.class, Variable::new),

            new TerminalBuildRule(MinstrelParser.TypeContext.class),
            new TerminalBuildRule(MinstrelParser.NameContext.class)
            );

    private static Map<Class<? extends ParserRuleContext>, BuildRule> buildRules
            = allRules.stream().collect(Collectors.toMap(BuildRule::getContextType, rule -> rule));
}
