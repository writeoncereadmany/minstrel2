package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.ArgumentList;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class FunctionCall implements Expression
{
    public final Expression function;
    public final ArgumentList args;

    public FunctionCall(Expression function, ArgumentList args)
    {
        this.function = function;
        this.args = args;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionCall(this);
    }

}
