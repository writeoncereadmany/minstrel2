package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class TypeList implements AstNode
{
    public final List<TypeExpression> types;

    public TypeList(List<TypeExpression> types)
    {
        this.types = unmodifiableList(types);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitTypeList(types);
    }

}
