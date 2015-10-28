package com.writeoncereadmany.minstrel.compile.ast;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Program implements AstNode
{
    public final List<Statement> statements;
    private final Source source;

    public Program(Source source, List<Statement> statements)
    {
        this.source = source;
        this.statements = unmodifiableList(statements);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitProgram(this);
    }

    @Override
    public Source getSource()
    {
        return source;
    }

}
