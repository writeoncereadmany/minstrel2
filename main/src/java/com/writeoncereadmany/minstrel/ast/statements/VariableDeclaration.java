package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.ast.Terminal;
import com.writeoncereadmany.minstrel.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.names.NameResolver;
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
    public void defineNames(NameResolver nameResolver)
    {
        nameResolver.defineValue(name);
        expression.defineNames(nameResolver);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        typeIndex = nameResolver.resolveType(type);
        expression.resolveNames(nameResolver);
    }
}
