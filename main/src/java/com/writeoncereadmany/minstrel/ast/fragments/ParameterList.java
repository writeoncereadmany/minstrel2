package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class ParameterList implements AstNode
{
    private final List<Parameter> parameters;

    public ParameterList(List<Parameter> parameters)
    {
        this.parameters = parameters;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        parameters.forEach(parameter -> parameter.defineNames(nameResolver));
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        parameters.forEach(parameter -> parameter.resolveNames(nameResolver));
    }
}
