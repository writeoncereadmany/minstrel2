package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class Parameter implements AstNode
{
    private final String type;
    private final String name;

    public Parameter(String type, String name)
    {
        this.type = type;
        this.name = name;
    }

    @Override
    public void defineNames(NameResolver nameResolver) {

    }

    @Override
    public void resolveNames(NameResolver nameResolver) {

    }
}
