package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.expressions.Expression;

public class DeclarationStatement implements Statement
{
    private final String type;
    private final String name;
    private final Expression expression;

    public DeclarationStatement(String type, String name, Expression expression)
    {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }
}
