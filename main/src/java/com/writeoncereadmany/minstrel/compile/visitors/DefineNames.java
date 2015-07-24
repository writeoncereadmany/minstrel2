package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;

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
        nameResolver.define(name, Kind.VALUE);
        expression.visit(this);
    }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function)
    {
        nameResolver.define(name, Kind.VALUE);
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
        nameResolver.define(name, Kind.VALUE);
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
