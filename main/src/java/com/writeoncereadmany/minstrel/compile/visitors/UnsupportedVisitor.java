package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;

/**
 * A visitor which, by default, fails on every operation,
 * for when we only want to be able to visit a subset of operations
 * and we want to know if we're trying to do things we can't.
 */
public class UnsupportedVisitor implements AstVisitor
{
    @Override
    public void visitProgram(Program program)
    {
        throw new UnsupportedOperationException("Visiting programs not supported by " + getClass().getName());
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration)
    {
        throw new UnsupportedOperationException("Visiting variable declarations not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration function)
    {
        throw new UnsupportedOperationException("Visiting function declarations not supported by " + getClass().getName());
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement)
    {
        throw new UnsupportedOperationException("Visiting expression statements not supported by " + getClass().getName());
    }

    @Override
    public void visitParameterList(ParameterList parameters)
    {
        throw new UnsupportedOperationException("Visiting parameter lists not supported by " + getClass().getName());
    }

    @Override
    public void visitParameter(Parameter parameter)
    {
        throw new UnsupportedOperationException("Visiting parameters not supported by " + getClass().getName());
    }

    @Override
    public void visitBody(Body body)
    {
        throw new UnsupportedOperationException("Visiting function bodies not supported by " + getClass().getName());
    }

    @Override
    public void visitArgumentList(ArgumentList list)
    {
        throw new UnsupportedOperationException("Visiting argument lists not supported by " + getClass().getName());
    }

    @Override
    public void visitVariable(Variable variable)
    {
        throw new UnsupportedOperationException("Visiting variables not supported by " + getClass().getName());
    }

    @Override
    public void visitStringLiteral(StringLiteral literal)
    {
        throw new UnsupportedOperationException("Visiting string literals not supported by " + getClass().getName());
    }

    @Override
    public void visitNumberLiteral(NumberLiteral number)
    {
        throw new UnsupportedOperationException("Visiting number literals not supported by " + getClass().getName());
    }

    @Override
    public void visitMemberAccess(MemberAccess memberAccess)
    {
        throw new UnsupportedOperationException("Visiting member accesses not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall)
    {
        throw new UnsupportedOperationException("Visiting function calls not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionExpression(FunctionExpression functionExpression)
    {
        throw new UnsupportedOperationException("Visiting functions not supported by " + getClass().getName());
    }

    @Override
    public void visitNamedType(NamedType namedType)
    {
        throw new UnsupportedOperationException("Visiting named types not supported by " + getClass().getName());
    }

    @Override
    public void visitTypeList(TypeList types)
    {
        throw new UnsupportedOperationException("Visiting argument type lists not supported by " + getClass().getName());
    }

    @Override
    public void visitFunctionTypeLiteral(FunctionTypeLiteral literal)
    {
        throw new UnsupportedOperationException("Visiting function type literals not supported by " + getClass().getName());
    }
}
