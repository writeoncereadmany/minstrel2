package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class Body implements AstNode
{
    private final List<Statement> statements;

    public Body(List<Statement> statements)
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
