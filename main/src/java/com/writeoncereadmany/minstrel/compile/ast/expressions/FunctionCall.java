package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class FunctionCall implements Expression
{
    private final Source source;
    public final Expression function;
    public final ArgumentList args;

    public FunctionCall(Source source, Expression function, ArgumentList args)
    {
        this.source = source;
        this.function = function;
        this.args = args;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionCall(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return function.type().returnType();
    }
}
