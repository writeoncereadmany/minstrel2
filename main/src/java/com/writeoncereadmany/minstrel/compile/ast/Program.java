package com.writeoncereadmany.minstrel.compile.ast;

import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

public class Program implements AstNode
{
    private final List<Statement> statements;

    public Program(List<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitProgram(statements);
    }

}
