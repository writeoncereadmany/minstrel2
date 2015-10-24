package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class FunctionTypeLiteral implements TypeExpression
{
    private final TypeList typeList;
    private final TypeExpression typeExpression;

    public FunctionTypeLiteral(TypeList typeList, TypeExpression typeExpression) {
        this.typeList = typeList;

        this.typeExpression = typeExpression;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionTypeLiteral(typeList, typeExpression);
    }
}
