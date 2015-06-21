package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class Block implements AstNode
{
    private final List<Statement> statements;

    public Block(List<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        statements.forEach(statement -> statement.defineNames(nameResolver));
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        statements.forEach(statement -> statement.resolveNames(nameResolver));
    }
}
