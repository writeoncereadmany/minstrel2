package com.writeoncereadmany.minstrel.ast;

import com.writeoncereadmany.minstrel.ast.statements.Statement;
import com.writeoncereadmany.minstrel.names.NameResolver;

import java.util.List;

public class Block implements AstNode
{
    public Block(List<Statement> statements)
    {

    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {

    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {

    }
}
