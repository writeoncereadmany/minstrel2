package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;

public class NoOpVisitor implements AstVisitor
{
    @Override
    public void visitProgram(Program program) {

    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration) {

    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration) {

    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {

    }

    @Override
    public void visitParameterList(ParameterList parameters) {

    }

    @Override
    public void visitParameter(Parameter parameter) {

    }

    @Override
    public void visitBody(Body body) {

    }

    @Override
    public void visitArgumentList(ArgumentList arguments) {

    }

    @Override
    public void visitVariable(Variable variable) {

    }

    @Override
    public void visitStringLiteral(StringLiteral literal) {

    }

    @Override
    public void visitNumberLiteral(NumberLiteral literal) {

    }

    @Override
    public void visitMemberAccess(MemberAccess memberAccess) {

    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall) {

    }

    @Override
    public void visitFunctionExpression(FunctionExpression functionExpression) {

    }

    @Override
    public void visitNamedType(NamedType namedType) {

    }

    @Override
    public void visitTypeList(TypeList typeList) {

    }

    @Override
    public void visitFunctionTypeLiteral(FunctionTypeLiteral functionTypeLiteral) {

    }
}
