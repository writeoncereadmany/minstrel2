package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.names.ScopeIndex;

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
