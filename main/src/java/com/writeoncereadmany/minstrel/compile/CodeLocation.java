package com.writeoncereadmany.minstrel.compile;

import org.antlr.v4.runtime.Token;

public class CodeLocation
{
    public final int line;
    public final int column;

    public CodeLocation(int line, int column)
    {
        this.line = line;
        this.column = column;
    }

    public static CodeLocation fromToken(Token token)
    {
        return new CodeLocation(token.getLine(), token.getCharPositionInLine());
    }
}
