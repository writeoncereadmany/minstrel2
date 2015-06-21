package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.Body;
import com.writeoncereadmany.minstrel.ast.Terminal;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class FunctionDeclaration implements Statement
{
    private final Terminal name;
    private final ParameterList parameters;
    private final Body body;

    private int bodyScope;

    public FunctionDeclaration(Terminal name, ParameterList parameters, Body body)
    {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        nameResolver.defineValue(name);
        bodyScope = nameResolver.enterNewScope();
        parameters.defineNames(nameResolver);
        body.defineNames(nameResolver);
        nameResolver.exitScope(bodyScope);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        nameResolver.enterExistingScope(bodyScope);
        parameters.resolveNames(nameResolver);
        body.resolveNames(nameResolver);
        nameResolver.exitScope(bodyScope);
    }
}
