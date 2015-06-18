package com.writeoncereadmany.minstrel.ast.expressions;

public class Variable implements Expression
{
    private final String name;

    public Variable(String name)
    {
        this.name = name;
    }
}
