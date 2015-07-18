package com.writeoncereadmany.minstrel.visitors;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.ast.expressions.Function;
import com.writeoncereadmany.minstrel.ast.fragments.*;
import com.writeoncereadmany.minstrel.ast.statements.Statement;

import java.util.List;

public interface AstVisitor
{
    void visitProgram(List<Statement> statements);
    void visitVariableDeclaration(Terminal type, Terminal name, Expression expression);
    void visitFunctionDeclaration(Terminal name, Function function);
    void visitExpressionStatement(Expression expression);
    void visitParameterList(List<Parameter> parameters);
    void visitParameter(Terminal type, Terminal name);
    void visitBody(List<Statement> statements);
    void visitArgumentList(List<Expression> expressions);
    void visitVariable(Terminal name);
    void visitStringLiteral(Terminal value);
    void visitNumberLiteral(Terminal value);
    void visitMemberAccess(Expression expression, Terminal memberName);
    void visitFunctionCall(Expression function, ArgumentList args);
    void visitFunction(ParameterList parameterList, Body body);
}
