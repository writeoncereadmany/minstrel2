package com.writeoncereadmany.minstrel.ast.expressions;

public class StringLiteral implements Expression
{
    private final String value;

    public StringLiteral(String value)
    {
        this.value = value;
    }
}
