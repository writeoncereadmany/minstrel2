package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ParameterList implements AstNode
{
    private final Source source;
    public final List<Parameter> parameters;

    public ParameterList(Source source, List<Parameter> parameters)
    {
        this.source = source;
        this.parameters = unmodifiableList(parameters);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitParameterList(this);
    }

    @Override
    public Source getSource()
    {
        return source;
    }

}
