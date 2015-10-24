package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;

import java.util.List;

public class ResolveNames extends NoOpVisitor
{
    private final NameResolver nameResolver;
    private int currentScope;

    public ResolveNames(NameResolver nameResolver)
    {
        this.nameResolver = nameResolver;
    }

    @Override
    public void visitProgram(List<Statement> statements)
    {
        statements.forEach(this::visit);
    }

    @Override
    public void visitVariableDeclaration(TypeExpression type, Terminal name, Expression expression)
    {
        visit(type);
        visit(expression);
    }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function)
    {
        visit(function);
    }

    @Override
    public void visitExpressionStatement(Expression expression)
    {
        visit(expression);
    }

    @Override
    public void visitParameterList(List<Parameter> parameters)
    {
        parameters.forEach(this::visit);
    }

    @Override
    public void visitParameter(TypeExpression type, Terminal name)
    {
        visit(type);
    }

    @Override
    public void visitBody(List<Statement> statements)
    {
        statements.forEach(this::visit);
    }

    @Override
    public void visitArgumentList(List<Expression> expressions)
    {
        expressions.forEach(this::visit);
    }

    @Override
    public void visitVariable(Terminal name)
    {
        nameResolver.resolve(name, Kind.VALUE);
    }

    @Override
    public void visitMemberAccess(Expression expression, Terminal memberName)
    {
        expression.visit(this);
    }

    @Override
    public void visitFunctionCall(Expression function, ArgumentList args)
    {
        visit(function);
        visit(args);
    }

    @Override
    public void visitFunction(ParameterList parameterList, Body body)
    {
        enterScope();
        final int bodyScope = getCurrentScope();

        nameResolver.enterExistingScope(bodyScope);
        visit(parameterList);
        visit(body);
        nameResolver.exitScope(bodyScope);
    }

    @Override
    public void visitNamedType(Terminal typeName)
    {
        nameResolver.resolve(typeName, Kind.TYPE);;
    }

    private void enterScope()
    {
        currentScope++;
    }

    private int getCurrentScope()
    {
        return currentScope;
    }

    private void visit(AstNode node)
    {
        node.visit(this);
    }
}
