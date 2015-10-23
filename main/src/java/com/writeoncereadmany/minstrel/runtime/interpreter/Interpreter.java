package com.writeoncereadmany.minstrel.runtime.interpreter;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.number.InefficientRatio;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.CustomFunction;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelNumber;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelString;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Interpreter implements AstVisitor
{
    private final NameResolver nameResolver;
    private final Stack<Environment> stackFrames = new Stack<>();
    private final Stack<Value> evaluationStack = new Stack<>();
    private Value lastEvaluatedValue = null;
    private Stack<Queue<Value>> arguments = new Stack<>();
    private Queue<Value> currentArguments = null;

    public Interpreter(NameResolver nameResolver, Environment prelude)
    {
        this.nameResolver = nameResolver;
        stackFrames.push(prelude);
    }

    @Override
    public void visitProgram(List<Statement> statements) 
    {
        enterNewScope();
        statements.forEach(this::visit);
        exitScope();
    }

    @Override
    public void visitVariableDeclaration(Terminal type, Terminal name, Expression expression)
    {
        visit(expression);
        currentEnvironment().declare(valueFor(name), evaluate(expression));
    }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function)
    {
        currentEnvironment().declare(valueFor(name), evaluate(function));
    }

    @Override
    public void visitExpressionStatement(Expression expression)
    {
        evaluate(expression);
    }

    @Override
    public void visitParameterList(List<Parameter> parameters)
    {
        currentArguments = arguments.pop();
        parameters.forEach(this::visit);
        currentArguments = null;
    }

    @Override
    public void visitParameter(Terminal type, Terminal name)
    {
        currentEnvironment().declare(valueFor(name), nextArgument());
    }

    @Override
    public void visitBody(List<Statement> statements)
    {
        statements.forEach(this::visit);
        store(lastEvaluatedValue);
    }

    @Override
    public void visitArgumentList(List<Expression> expressions)
    {
        arguments.push(expressions.stream().map(this::evaluate).collect(Collectors.toCollection(ArrayDeque::new)));
    }

    @Override
    public void visitVariable(Terminal name)
    {
        store(currentEnvironment().get(valueFor(name)));
    }

    @Override
    public void visitStringLiteral(Terminal value)
    {
        store(MinstrelString.fromLiteral(value.text));
    }

    @Override
    public void visitNumberLiteral(Terminal value)
    {
        store(new MinstrelNumber(InefficientRatio.parse(value.text)));
    }

    @Override
    public void visitMemberAccess(Expression expression, Terminal memberName)
    {
        Value record = evaluate(expression);
        store(record.get(memberName.text));
    }

    @Override
    public void visitFunctionCall(Expression function, ArgumentList args)
    {
        Value toCall = evaluate(function);
        visit(args);
        store(toCall.call(this, arguments.pop().toArray(new Value[arguments.size()])));
    }

    @Override
    public void visitFunction(ParameterList parameterList, Body body)
    {
        store(new CustomFunction(parameterList, body));
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

    public void exitScope()
    {
        stackFrames.pop();
    }

    private Value nextArgument()
    {
        return currentArguments.poll();
    }

    public Value consume()
    {
        return evaluationStack.pop();
    }

    public Queue<Value> getArguments()
    {
        return arguments.pop();
    }

    public void setArguments(Value... addend)
    {
        arguments.push(new ArrayDeque<>(asList(addend)));
    }

    public void populateArguments(ParameterList parameterList, List<Value> objects)
    {
        FunctionEnvironmentPopulator populator = new FunctionEnvironmentPopulator(nameResolver, currentEnvironment(), objects);
        parameterList.visit(populator);
    }
}
