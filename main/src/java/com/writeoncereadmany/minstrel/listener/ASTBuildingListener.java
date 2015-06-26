package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.ast.expressions.NumberLiteral;
import com.writeoncereadmany.minstrel.ast.expressions.StringLiteral;
import com.writeoncereadmany.minstrel.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.astbuilders.BodyBuilder;
import com.writeoncereadmany.minstrel.astbuilders.ProgramBuilder;
import com.writeoncereadmany.minstrel.astbuilders.expressions.FunctionBuilder;
import com.writeoncereadmany.minstrel.astbuilders.expressions.FunctionCallBuilder;
import com.writeoncereadmany.minstrel.astbuilders.expressions.PlusExpressionBuilder;
import com.writeoncereadmany.minstrel.astbuilders.fragments.ArgumentListBuilder;
import com.writeoncereadmany.minstrel.astbuilders.fragments.ParameterBuilder;
import com.writeoncereadmany.minstrel.astbuilders.fragments.ParameterListBuilder;
import com.writeoncereadmany.minstrel.astbuilders.statements.FunctionDeclarationBuilder;
import com.writeoncereadmany.minstrel.astbuilders.statements.VariableDeclarationBuilder;
import com.writeoncereadmany.minstrel.astbuilders.statements.ExpressionStatementBuilder;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelBaseListener;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ASTBuildingListener extends MinstrelBaseListener
{
    private final ASTBuilder builder;

    public ASTBuildingListener(ASTBuilder builder)
    {
        this.builder = builder;
    }

    @Override
    public void enterEveryRule(@NotNull ParserRuleContext ctx)
    {
        buildRules.getOrDefault(ctx.getClass(), SkipRule.INSTANCE)
                  .onEnter(ctx, builder);
    }

    @Override
    public void exitEveryRule(@NotNull ParserRuleContext ctx)
    {
        buildRules.getOrDefault(ctx.getClass(), SkipRule.INSTANCE)
                  .onExit(ctx, builder);
    }

    private static List<RuleProcessor> allRules = asList(
            new BuildRootNode(MinstrelParser.ProgramContext.class, ProgramBuilder::new),

            new BuildCompoundNode(MinstrelParser.Expression_bodyContext.class, BodyBuilder::new),
            new BuildCompoundNode(MinstrelParser.BlockContext.class, BodyBuilder::new),
            new BuildCompoundNode(MinstrelParser.Variable_declarationContext.class, VariableDeclarationBuilder::new),
            new BuildCompoundNode(MinstrelParser.Function_declarationContext.class, FunctionDeclarationBuilder::new),
            new BuildCompoundNode(MinstrelParser.Expression_statementContext.class, ExpressionStatementBuilder::new),
            new BuildCompoundNode(MinstrelParser.Function_callContext.class, FunctionCallBuilder::new),
            new BuildCompoundNode(MinstrelParser.Plus_expressionContext.class, PlusExpressionBuilder::new),
            new BuildCompoundNode(MinstrelParser.Argument_listContext.class, ArgumentListBuilder::new),
            new BuildCompoundNode(MinstrelParser.FunctionContext.class, FunctionBuilder::new),
            new BuildCompoundNode(MinstrelParser.Parameter_listContext.class, ParameterListBuilder::new),
            new BuildCompoundNode(MinstrelParser.ParameterContext.class, ParameterBuilder::new),

            new BuildTextNode(MinstrelParser.Number_literalContext.class, NumberLiteral::new),
            new BuildTextNode(MinstrelParser.String_literalContext.class, StringLiteral::new),
            new BuildTextNode(MinstrelParser.VariableContext.class, Variable::new),

            new BuildTerminal(MinstrelParser.TypeContext.class),
            new BuildTerminal(MinstrelParser.NameContext.class)
            );

    private static Map<Class<? extends ParserRuleContext>, RuleProcessor> buildRules
            = allRules.stream().collect(Collectors.toMap(RuleProcessor::getContextType, rule -> rule));
}
