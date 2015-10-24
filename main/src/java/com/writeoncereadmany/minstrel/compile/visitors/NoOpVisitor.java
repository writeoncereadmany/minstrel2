package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;

import java.util.List;

public class NoOpVisitor implements AstVisitor
{
    @Override
    public void visitProgram(List<Statement> statements) { }

    @Override
    public void visitVariableDeclaration(TypeExpression type, Terminal name, Expression expression) { }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function) { }

    @Override
    public void visitExpressionStatement(Expression expression) { }

    @Override
    public void visitParameterList(List<Parameter> parameters) { }

    @Override
    public void visitParameter(TypeExpression type, Terminal name) { }

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

    @Override
    public void visitNamedType(Terminal typeName) { }
}
