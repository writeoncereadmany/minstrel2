package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class ParameterList implements AstNode
{
    public ParameterList(List<Parameter> parameters)
    {

    }

    @Override
    public void defineNames(NameResolver nameResolver) {

    }

    @Override
    public void resolveNames(NameResolver nameResolver) {

    }
}
