package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;

public interface AstVisitor
{
    void visitProgram(Program program);
    void visitVariableDeclaration(VariableDeclaration declaration);
    void visitFunctionDeclaration(FunctionDeclaration declaration);
    void visitExpressionStatement(ExpressionStatement statement);
    void visitParameterList(ParameterList parameters);
    void visitParameter(Parameter parameter);
    void visitBody(Body body);
    void visitArgumentList(ArgumentList arguments);
    void visitVariable(Variable variable);
    void visitStringLiteral(StringLiteral literal);
    void visitNumberLiteral(NumberLiteral literal);
    void visitMemberAccess(MemberAccess memberAccess);
    void visitFunctionCall(FunctionCall functionCall);
    void visitFunctionExpression(FunctionExpression functionExpression);
    void visitNamedType(NamedType namedType);
    void visitTypeList(TypeList typeList);
    void visitFunctionTypeLiteral(FunctionTypeLiteral functionTypeLiteral);

    default void visit(AstNode node)
    {
        node.visit(this);
    }
}
