package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Variable;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.IndeterminateType;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;

import java.util.Map;

public class ResolveNames extends NoOpVisitor
{
    private final NameResolver nameResolver;
    private final Map<ScopeIndex, Typed> typeResolver;
    private int currentScope;

    public ResolveNames(NameResolver nameResolver, Map<ScopeIndex, Typed> typeResolver)
    {
        this.nameResolver = nameResolver;
        this.typeResolver = typeResolver;
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
        variable.setType(typeResolver.computeIfAbsent(variable.name.scopeIndex(),
                                                      __ -> new IndeterminateType("Cannot determine type for " + variable.name.text)));
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

    @Override
    public void visitFunctionTypeLiteral(FunctionTypeLiteral functionTypeLiteral)
    {
        visit(functionTypeLiteral.parameters);
        visit(functionTypeLiteral.returnType);
    }

    @Override
    public void visitTypeList(TypeList typeList) {
        typeList.types.forEach(this::visit);
    }

    private void enterScope()
    {
        currentScope++;
    }

    private int getCurrentScope()
    {
        return currentScope;
    }
}
