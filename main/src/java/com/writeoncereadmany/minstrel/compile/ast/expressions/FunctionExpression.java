package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class FunctionExpression implements Expression
{
    public final ParameterList parameterList;
    public final Body body;

    public FunctionExpression(ParameterList parameterList, Body body)
    {
        this.parameterList = parameterList;
        this.body = body;
    }

    @Override
    public void visit(AstVisitor visitor) 
    {
        visitor.visitFunctionExpression(this);
    }

    @Override
    public TypeDefinition type(TypeChecker checker)
    {
        return body.type(checker);
    }
}
