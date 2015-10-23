package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.Collections;
import java.util.List;

public class ParameterList implements AstNode
{
    public final List<Parameter> parameters;

    public ParameterList(List<Parameter> parameters)
    {
        this.parameters = Collections.unmodifiableList(parameters);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameterList(parameters);
    }

}
