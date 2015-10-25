package com.writeoncereadmany.minstrel.compile.ast;

import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Program implements AstNode
{
    public final List<Statement> statements;

    public Program(List<Statement> statements)
    {
        this.statements = unmodifiableList(statements);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitProgram(statements);
    }

}
