package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ParameterList implements AstNode
{
    public final List<Parameter> parameters;

    public ParameterList(List<Parameter> parameters)
    {
        this.parameters = unmodifiableList(parameters);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameterList(this);
    }

}
