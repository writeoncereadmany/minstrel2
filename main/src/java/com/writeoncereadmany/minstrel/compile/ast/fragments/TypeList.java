package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class TypeList implements AstNode
{
    private final Source source;
    public final List<TypeExpression> types;

    public TypeList(Source source, List<TypeExpression> types)
    {
        this.source = source;
        this.types = unmodifiableList(types);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitTypeList(this);
    }

    @Override
    public Source getSource()
    {
        return source;
    }
}
