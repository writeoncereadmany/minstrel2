package com.writeoncereadmany.minstrel.compile.ast.statements;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class VariableDeclaration implements Statement
{
    private final Terminal type;
    private final Terminal name;
    private final Expression expression;

    private ScopeIndex typeIndex;

    public VariableDeclaration(Terminal type, Terminal name, Expression expression)
    {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitVariableDeclaration(type, name, expression);
    }

}
