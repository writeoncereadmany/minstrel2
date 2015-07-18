package com.writeoncereadmany.minstrel.visitors;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.Function;
import com.writeoncereadmany.minstrel.ast.fragments.*;
import com.writeoncereadmany.minstrel.ast.statements.Statement;

import java.util.List;

public class NoOpVisitor implements AstVisitor
{
    @Override
    public void visitProgram(List<Statement> statements) { }

    @Override
    public void visitVariableDeclaration(Terminal type, Terminal name, Expression expression) { }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function) { }

    @Override
    public void visitExpressionStatement(Expression expression) { }

    @Override
    public void visitParameterList(List<Parameter> parameters) { }

    @Override
    public void visitParameter(Terminal type, Terminal name) { }

    @Override
    public void visitBody(List<Statement> statements) { }

    @Override
    public void visitArgumentList(List<Expression> expressions) { }

    @Override
    public void visitVariable(Terminal name) { }

    @Override
    public void visitStringLiteral(Terminal value) { }

    @Override
    public void visitNumberLiteral(Terminal value) { }

    @Override
    public void visitMemberAccess(Expression expression, Terminal memberName) { }

    @Override
    public void visitFunctionCall(Expression function, ArgumentList args) { }

    @Override
    public void visitFunction(ParameterList parameterList, Body body) { }
}
