package com.writeoncereadmany.minstrel.ast.fragments;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.statements.Statement;

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
