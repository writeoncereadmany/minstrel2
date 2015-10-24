package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.Collections;
import java.util.List;

public class TypeList implements AstNode
{
    public final List<TypeExpression> argumentTypes;

    public TypeList(List<TypeExpression> argumentTypes)
    {
        this.argumentTypes = Collections.unmodifiableList(argumentTypes);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitTypeList(argumentTypes);
    }

}
