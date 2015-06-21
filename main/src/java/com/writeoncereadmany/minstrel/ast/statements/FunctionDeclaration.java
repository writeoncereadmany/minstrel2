package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.Block;
import com.writeoncereadmany.minstrel.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class FunctionDeclaration implements Statement
{
    private final String name;
    private final ParameterList parameters;
    private final Block body;

    public FunctionDeclaration(String name, ParameterList parameters, Block body)
    {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {

    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {

    }
}
