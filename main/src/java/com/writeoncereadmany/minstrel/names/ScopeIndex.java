package com.writeoncereadmany.minstrel.names;

import java.util.Objects;

/**
 * Numeric reference to a declaration somewhere in code: always unique. Made up of two parts: a scope index and a
 * position index.
 *
 * Each scope has a unique index, ordered by declaration order. That means a higher scope index is not necessarily
 * a nested scope of a lower scope index. As we define scopes, they're aware of which scopes are their parents.
 * This limits us to 2 billion-ish scopes in a program, which seems plenty.
 *
 * Position index is index of definition order within
 */
public class ScopeIndex
{
    public static final ScopeIndex UNDEFINED = new ScopeIndex(Integer.MIN_VALUE, Integer.MIN_VALUE);

    private final int scopeIndex;
    private final int positionIndex;

    public ScopeIndex(int scopeIndex, int positionIndex)
    {
        this.scopeIndex = scopeIndex;
        this.positionIndex = positionIndex;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScopeIndex that = (ScopeIndex) o;
        return scopeIndex == that.scopeIndex && positionIndex == that.positionIndex;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(scopeIndex, positionIndex);
    }

    @Override
    public String toString() {
        return "ScopeIndex{" +
                "scopeIndex=" + scopeIndex +
                ", positionIndex=" + positionIndex +
                '}';
    }
}
