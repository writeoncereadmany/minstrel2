package com.writeoncereadmany.minstrel.compile.visitors;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.ast.expressions.*;
import com.writeoncereadmany.minstrel.compile.ast.fragments.*;
import com.writeoncereadmany.minstrel.compile.ast.statements.ExpressionStatement;
import com.writeoncereadmany.minstrel.compile.ast.statements.FunctionDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.statements.VariableDeclaration;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.NamedType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.util.Pair;
import com.writeoncereadmany.util.Zipper;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TypeChecker implements AstVisitor
{
    private final TypeEngine typeEngine;
    private final List<TypeError> typeErrors = new ArrayList<>();

    public TypeChecker(TypeEngine typeEngine)
    {
        this.typeEngine = typeEngine;
    }

    @Override
    public void visitProgram(Program program) 
    {
        // we can add the "each statement has to have type Success" rule later, let's start simple
        program.statements.forEach(this::visit);
    }

    @Override
    public void visitVariableDeclaration(VariableDeclaration declaration)
    {
        visit(declaration.expression);
        TypeDefinition result = declaration.expression.type();
        TypeDefinition variableType = declaration.type.type();
        checkCanAssign(result, variableType);
    }

    @Override
    public void visitFunctionDeclaration(FunctionDeclaration declaration)
    {
        visit(declaration.function);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement)
    {
        visit(statement.expression);
    }

    @Override
    public void visitParameterList(ParameterList parameters)
    {
        parameters.parameters.forEach(this::visit);
    }

    @Override
    public void visitParameter(Parameter parameter)
    {
        visit(parameter.type);
    }

    @Override
    public void visitBody(Body body)
    {
        body.statements.forEach(this::visit);
    }

    @Override
    public void visitArgumentList(ArgumentList arguments)
    {
        throw new UnsupportedOperationException("Should not iterate over arguments list directly: " +
                                                "this should be done manually from within a function call walk");
    }

    @Override
    public void visitVariable(Variable variable)
    {
        // a variable reference in itself will always typecheck: even if it has an incoherent type, that's handled at declaration
    }

    @Override
    public void visitStringLiteral(StringLiteral literal)
    {
        // a string literal will always typecheck
    }

    @Override
    public void visitNumberLiteral(NumberLiteral literal)
    {
        // a number literal will always typecheck
    }

    @Override
    public void visitMemberAccess(MemberAccess memberAccess)
    {
        visit(memberAccess.expression);
        typeEngine.checkCoherent(memberAccess.type());
    }

    @Override
    public void visitFunctionCall(FunctionCall functionCall)
    {
        visit(functionCall.function);
        Type type = functionCall.function.type().getType(typeEngine);
        FunctionType functionConcern = type.getConcern(FunctionType.class);

        if(functionConcern == null)
        {
            typeErrors.add(new TypeError("Not a function: cannot call"));
            return;
        }

        final List<TypeDefinition> argumentTypes = functionConcern.argumentTypes;

        List<Pair<TypeDefinition, Expression>> formalToProvidedTypes = Zipper.zip(argumentTypes, functionCall.args.expressions);
        formalToProvidedTypes.forEach(pair -> {
            TypeDefinition requiredType = pair.left;
            TypeDefinition providedType = pair.right.type();
            checkCanAssign(providedType, requiredType);
        });
    }

    @Override
    public void visitFunctionExpression(FunctionExpression functionExpression)
    {
        // as parameter type expressions could be incoherent
        visit(functionExpression.parameterList);
        visit(functionExpression.body);
    }

    @Override
    public void visitNamedType(NamedType namedType)
    {
        // if there were a problem with a named type, that'd have been a problem when we declared it
    }

    @Override
    public void visitTypeList(TypeList typeList)
    {
        typeList.types.forEach(this::visit);
    }

    @Override
    public void visitFunctionTypeLiteral(FunctionTypeLiteral functionTypeLiteral)
    {
        visit(functionTypeLiteral.parameters);
        visit(functionTypeLiteral.returnType);
    }

    private boolean checkCanAssign(TypeDefinition sourceType, TypeDefinition targetType)
    {
        return typeErrors.addAll(typeEngine.canAssign(sourceType, targetType).collect(toList()));
    }

    public List<TypeError> getTypeErrors()
    {
        return typeErrors;
    }
}
