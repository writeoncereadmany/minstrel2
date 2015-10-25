package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;

public class ResolveNames extends NoOpVisitor
{
    private final NameResolver nameResolver;
    private int currentScope;

    public ResolveNames(NameResolver nameResolver)
    {
        this.nameResolver = nameResolver;
    }

    @Override
    public void visitProgram(Program program)
    {
        program.statements.forEach(this::visit);
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration)
    {
        nameResolver.resolve(declaration.name, Kind.VALUE);
        visit(declaration.type);
        visit(declaration.expression);
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration)
    {
        nameResolver.resolve(declaration.name, Kind.VALUE);
        visit(declaration.function);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement)
    {
        visit(statement.expression);
    }

    @Override
    public void visitParameterList(ParameterList parameterList)
    {
        parameterList.parameters.forEach(this::visit);
    }

    @Override
    public void visitParameter(Parameter parameter)
    {
        nameResolver.resolve(parameter.name, Kind.VALUE);
        visit(parameter.type);
    }

    @Override
    public void visitBody(Body body)
    {
        body.statements.forEach(this::visit);
    }

    @Override
    public void visitArgumentList(ArgumentList list)
    {
        list.expressions.forEach(this::visit);
    }

    @Override
    public void visitVariable(Variable variable)
    {
        nameResolver.resolve(variable.name, Kind.VALUE);
    }

    @Override
    public void visitMemberAccess(MemberAccess access)
    {
        access.expression.visit(this);
    }

    @Override
    public void visitFunctionCall(FunctionCall call)
    {
        visit(call.function);
        visit(call.args);
    }

    @Override
    public void visitFunctionExpression(FunctionExpression function)
    {
        enterScope();
        final int bodyScope = getCurrentScope();

        nameResolver.enterExistingScope(bodyScope);
        visit(function.parameterList);
        visit(function.body);

        nameResolver.exitScope(bodyScope);
    }

    @Override
    public void visitNamedType(NamedType type)
    {
        nameResolver.resolve(type.name, Kind.TYPE);;
    }

    private void enterScope()
    {
        currentScope++;
    }

    private int getCurrentScope()
    {
        return currentScope;
    }

    private void visit(AstNode node)
    {
        node.visit(this);
    }
}
