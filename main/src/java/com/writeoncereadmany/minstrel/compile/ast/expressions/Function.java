package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class Function implements Expression
{
    public final ParameterList parameterList;
    public final Body body;

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
