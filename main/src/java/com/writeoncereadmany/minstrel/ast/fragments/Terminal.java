package com.writeoncereadmany.minstrel.ast.fragments;

public class Terminal
{
    public final String text;
    public final int line;
    public final int column;

    public Terminal(String text, int line, int column)
    {
        this.text = text;
        this.line = line;
        this.column = column;
    }
}
