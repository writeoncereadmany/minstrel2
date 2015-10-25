package com.writeoncereadmany.minstrel.runtime.interpreter;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.visitors.UnsupportedVisitor;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.number.InefficientRatio;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.CustomFunction;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelNumber;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelString;
import com.writeoncereadmany.util.Pair;
import com.writeoncereadmany.util.Zipper;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Interpreter extends UnsupportedVisitor
{
    private final NameResolver nameResolver;
    private final Stack<Environment> stackFrames = new Stack<>();
    private final Stack<Value> evaluationStack = new Stack<>();
    private Value lastEvaluatedValue = null;

    public Interpreter(NameResolver nameResolver, Environment prelude)
    {
        this.nameResolver = nameResolver;
        stackFrames.push(prelude);
    }

    @Override
    public void visitProgram(Program program)
    {
        enterNewScope();
        program.statements.forEach(this::visit);
        exitScope();
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration)
    {
        // TODO why do we have this line here?
        visit(declaration.expression);
        currentEnvironment().declare(valueFor(declaration.name), evaluate(declaration.expression));
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration)
    {
        currentEnvironment().declare(valueFor(declaration.name), evaluate(declaration.function));
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement)
    {
        evaluate(statement.expression);
    }

    @Override
    public void visitBody(Body body)
    {
        body.statements.forEach(this::visit);
        store(lastEvaluatedValue);
    }

    @Override
    public void visitVariable(Variable variable)
    {
        store(currentEnvironment().get(valueFor(variable.name)));
    }

    @Override
    public void visitStringLiteral(StringLiteral literal)
    {
        store(MinstrelString.fromLiteral(literal.value.text));
    }

    @Override
    public void visitNumberLiteral(NumberLiteral literal)
    {
        store(new MinstrelNumber(InefficientRatio.parse(literal.value.text)));
    }

    @Override
    public void visitMemberAccess(MemberAccess access)
    {
        Value record = evaluate(access.expression);
        store(record.get(access.memberName.text));
    }

    @Override
    public void visitFunctionCall(FunctionCall call)
    {
        Value toCall = evaluate(call.function);
        final List<Value> arguments = call.args.expressions.stream().map(this::evaluate).collect(Collectors.toList());
        store(toCall.call(this, arguments));
    }

    @Override
    public void visitFunctionExpression(FunctionExpression function)
    {
        store(new CustomFunction(currentEnvironment(), function));
    }
    
    private void visit(AstNode node)
    {
        node.visit(this);
    }

    private ScopeIndex valueFor(Terminal name)
    {
        return nameResolver.lookup(name, Kind.VALUE);
    }

    public void store(Value item)
    {
        evaluationStack.push(item);
        lastEvaluatedValue = item;
    }

    private Value evaluate(Expression expression)
    {
        visit(expression);
        return consume();
    }

    private Environment currentEnvironment()
    {
        return stackFrames.peek();
    }

    public void enterNewScope()
    {
        stackFrames.push(stackFrames.peek().createChild());
    }

    public void enterScope(Environment environment)
    {
        stackFrames.push(environment);
    }

    public void exitScope()
    {
        stackFrames.pop();
    }

    public Value consume()
    {
        return evaluationStack.pop();
    }

    public void populateArguments(ParameterList parameterList, List<Value> arguments)
    {
        List<Pair<Parameter, Value>> parameters = Zipper.zip(parameterList.parameters, arguments);
        parameters.stream().forEach(pair ->
        {
            Parameter parameter = pair.left;
            Value argument = pair.right;
            currentEnvironment().declare(nameResolver.lookup(parameter.name, Kind.VALUE), argument);
        });
    }
}
