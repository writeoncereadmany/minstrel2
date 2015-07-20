package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;

import java.util.List;

public class ParameterList implements AstNode
{
    private final List<Parameter> parameters;

    public ParameterList(List<Parameter> parameters)
    {
        this.parameters = parameters;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameterList(parameters);
    }

}
