package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;

import java.util.List;

/**
 * A visitor which, by default, fails on every operation,
 * for when we only want to be able to visit a subset of operations
 * and we want to know if we're trying to do things we can't.
 */
public class UnsupportedVisitor implements AstVisitor
{
    @Override
    public void visitProgram(List<Statement> statements)
    {
        throw new UnsupportedOperationException("Visiting programs not supported by " + getClass().getName());
    }

    @Override
    public void visitVariableDeclaration(TypeExpression type, Terminal name, Expression expression)
    {
        throw new UnsupportedOperationException("Visiting variable declarations not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionDeclaration(Terminal name, Function function)
    {
        throw new UnsupportedOperationException("Visiting function declarations not supported by " + getClass().getName());
    }

    @Override
    public void visitExpressionStatement(Expression expression)
    {
        throw new UnsupportedOperationException("Visiting expression statements not supported by " + getClass().getName());
    }

    @Override
    public void visitParameterList(List<Parameter> parameters)
    {
        throw new UnsupportedOperationException("Visiting parameter lists not supported by " + getClass().getName());
    }

    @Override
    public void visitParameter(TypeExpression type, Terminal name)
    {
        throw new UnsupportedOperationException("Visiting parameters not supported by " + getClass().getName());
    }

    @Override
    public void visitBody(List<Statement> statements)
    {
        throw new UnsupportedOperationException("Visiting function bodies not supported by " + getClass().getName());
    }

    @Override
    public void visitArgumentList(List<Expression> expressions)
    {
        throw new UnsupportedOperationException("Visiting argument lists not supported by " + getClass().getName());
    }

    @Override
    public void visitVariable(Terminal name)
    {
        throw new UnsupportedOperationException("Visiting variables not supported by " + getClass().getName());
    }

    @Override
    public void visitStringLiteral(Terminal value)
    {
        throw new UnsupportedOperationException("Visiting string literals not supported by " + getClass().getName());
    }

    @Override
    public void visitNumberLiteral(Terminal value)
    {
        throw new UnsupportedOperationException("Visiting number literals not supported by " + getClass().getName());
    }

    @Override
    public void visitMemberAccess(Expression expression, Terminal memberName)
    {
        throw new UnsupportedOperationException("Visiting member accesses not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionCall(Expression function, ArgumentList args)
    {
        throw new UnsupportedOperationException("Visiting function calls not supported by " + getClass().getName());
    }

    @Override
    public void visitFunction(ParameterList parameterList, Body body)
    {
        throw new UnsupportedOperationException("Visiting functions not supported by " + getClass().getName());
    }

    @Override
    public void visitNamedType(Terminal typeName)
    {
        throw new UnsupportedOperationException("Visiting named types not supported by " + getClass().getName());
    }
}
