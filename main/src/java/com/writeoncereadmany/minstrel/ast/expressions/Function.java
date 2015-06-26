package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.ast.fragments.Body;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.names.NameResolver;

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
    public void defineNames(NameResolver nameResolver)
    {
        bodyScope = nameResolver.enterNewScope();
        parameterList.defineNames(nameResolver);
        body.defineNames(nameResolver);
        nameResolver.exitScope(bodyScope);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        nameResolver.enterExistingScope(bodyScope);
        parameterList.resolveNames(nameResolver);
        body.resolveNames(nameResolver);
        nameResolver.exitScope(bodyScope);
    }
}
