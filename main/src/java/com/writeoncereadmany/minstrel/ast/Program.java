package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.ast.statements.Statement;

import java.util.List;

public class Program implements AstNode
{
    private final List<Statement> statements;

    public Program(List<Statement> statements)
    {
        this.statements = statements;
    }
}
