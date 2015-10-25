package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.util.Hope;

import java.util.Objects;

public class Terminal
{
    public final String text;
    public final int line;
    public final int column;
    private final Hope<ScopeIndex> refersTo = new Hope<>();

    public Terminal(String text, int line, int column)
    {
        this.text = text;
        this.line = line;
        this.column = column;
    }

    public void setScopeIndex(ScopeIndex index)
    {
        refersTo.set(index);
    }

    public ScopeIndex scopeIndex()
    {
        return refersTo.get();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return line == terminal.line &&
                column == terminal.column &&
                text.equals(terminal.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, line, column);
    }
}
