package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionCall;
import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.expressions.MemberAccess;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;

import java.util.Map;

public class DefineNames extends NoOpVisitor
{
    private final NameResolver nameResolver;
    private final Map<ScopeIndex, Typed> typeResolver;

    public DefineNames(NameResolver nameResolver, Map<ScopeIndex, Typed> typeResolver)
    {
        this.nameResolver = nameResolver;
        this.typeResolver = typeResolver;
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
        typeResolver.put(declaration.name.scopeIndex(), declaration.type);
        declaration.expression.visit(this);
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration)
    {
        nameResolver.define(declaration.name, Kind.VALUE);
        typeResolver.put(declaration.name.scopeIndex(), declaration.function);
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
        typeResolver.put(parameter.name.scopeIndex(), parameter.type);
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
