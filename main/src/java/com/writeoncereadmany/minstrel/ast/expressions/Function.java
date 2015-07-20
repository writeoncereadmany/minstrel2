package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.ast.fragments.Body;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;

public class Function implements Expression
{
    private final ParameterList parameterList;
    private final Body body;

    private int bodyScope;

    public Function(ParameterList parameterList, Body body)
    {
        this.parameterList = parameterList;
        this.body = body;
    }

    @Override
    public void visit(AstVisitor visitor) 
    {
        visitor.visitFunction(parameterList, body);
    }

}
