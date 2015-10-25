package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
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
    public void visitProgram(Program program)
    {
        program.statements.forEach(statement -> statement.visit(this));
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration)
    {
        nameResolver.define(declaration.name, Kind.VALUE);
        declaration.expression.visit(this);
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration)
    {
        nameResolver.define(declaration.name, Kind.VALUE);
        declaration.function.visit(this);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement)
    {
        statement.expression.visit(this);
    }

    @Override
    public void visitParameterList(ParameterList list)
    {
        list.parameters.forEach(parameter -> parameter.visit(this));
    }

    @Override
    public void visitParameter(Parameter parameter)
    {
        nameResolver.define(parameter.name, Kind.VALUE);
    }

    @Override
    public void visitBody(Body body)
    {
        body.statements.forEach(statement -> statement.visit(this));
    }

    @Override
    public void visitArgumentList(ArgumentList arguments)
    {
        arguments.expressions.forEach(expression -> expression.visit(this));
    }

    @Override
    public void visitMemberAccess(MemberAccess access)
    {
        access.expression.visit(this);
    }

    @Override
    public void visitFunctionCall(FunctionCall call)
    {
        call.function.visit(this);
        call.args.visit(this);
    }

    @Override
    public void visitFunctionExpression(FunctionExpression function)
    {
        final int scope = nameResolver.enterNewScope();
        function.parameterList.visit(this);
        function.body.visit(this);
        nameResolver.exitScope(scope);
    }
}
