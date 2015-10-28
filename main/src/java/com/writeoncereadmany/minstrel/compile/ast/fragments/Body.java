package com.writeoncereadmany.minstrel.compile.ast.fragments;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.statements.Statement;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Body implements Typed, AstNode
{
    public final List<Statement> statements;
    private final Source source;

    public Body(Source source, List<Statement> statements)
    {
        this.source = source;
        this.statements = unmodifiableList(statements);
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitBody(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return statements.get(statements.size() - 1).type();
    }
}
