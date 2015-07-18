package com.writeoncereadmany.minstrel.visitors;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.Function;
import com.writeoncereadmany.minstrel.ast.fragments.*;
import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class DefineNames extends NoOpVisitor
{
    private final NameResolver nameResolver;

    public DefineNames(NameResolver nameResolver)
    {
        this.nameResolver = nameResolver;
    }

    @Override
    public void visitProgram(List<Statement> statements)
    {
        statements.forEach(statement -> statement.visit(this));
    }

    @Override
    public void visitVariableDeclaration(Terminal type, Terminal name, Expression expression)
    {
        nameResolver.defineValue(name);
        expression.visit(this);
    }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function)
    {
        nameResolver.defineValue(name);
        function.visit(this);
    }

    @Override
    public void visitExpressionStatement(Expression expression)
    {
        expression.visit(this);
    }

    @Override
    public void visitParameterList(List<Parameter> parameters)
    {
        parameters.forEach(parameter -> parameter.visit(this));
    }

    @Override
    public void visitParameter(Terminal type, Terminal name)
    {
        nameResolver.defineValue(name);
    }

    @Override
    public void visitBody(List<Statement> statements)
    {
        statements.forEach(statement -> statement.visit(this));
    }

    @Override
    public void visitArgumentList(List<Expression> expressions)
    {
        expressions.forEach(expression -> expression.visit(this));
    }

    @Override
    public void visitMemberAccess(Expression expression, Terminal memberName)
    {
        expression.visit(this);
    }

    @Override
    public void visitFunctionCall(Expression function, ArgumentList args)
    {
        function.visit(this);
        args.visit(this);
    }

    @Override
    public void visitFunction(ParameterList parameterList, Body body)
    {
        final int scope = nameResolver.enterNewScope();
        parameterList.visit(this);
        body.visit(this);
        nameResolver.exitScope(scope);
    }
}
