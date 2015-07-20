package com.writeoncereadmany.minstrel.ast.expressions;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.fragments.ArgumentList;

public class FunctionCall implements Expression
{
    private final Expression function;
    private final ArgumentList args;

    public FunctionCall(Expression function, ArgumentList args)
    {
        this.function = function;
        this.args = args;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionCall(function, args);
    }

}
