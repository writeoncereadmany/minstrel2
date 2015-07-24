package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

public class Body implements AstNode
{
    private final List<Statement> statements;

    public Body(List<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitBody(statements);
    }

}
