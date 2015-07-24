package com.writeoncereadmany.minstrel.compile.ast.statements;

import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

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

}
