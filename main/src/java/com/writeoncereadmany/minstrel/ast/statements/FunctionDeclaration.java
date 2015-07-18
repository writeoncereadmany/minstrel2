package com.writeoncereadmany.minstrel.ast.statements;

import com.writeoncereadmany.minstrel.visitors.AstVisitor;
import com.writeoncereadmany.minstrel.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.ast.expressions.Function;
import com.writeoncereadmany.minstrel.names.NameResolver;

public class FunctionDeclaration implements Statement
{
    private final Terminal name;
    private final Function function;

    public FunctionDeclaration(Terminal name, Function function)
    {
        this.name = name;
        this.function = function;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionDeclaration(name, function);
    }

    @Override
    public void defineNames(NameResolver nameResolver)
    {
        nameResolver.defineValue(name);
        function.defineNames(nameResolver);
    }

    @Override
    public void resolveNames(NameResolver nameResolver)
    {
        function.resolveNames(nameResolver);
    }
}
