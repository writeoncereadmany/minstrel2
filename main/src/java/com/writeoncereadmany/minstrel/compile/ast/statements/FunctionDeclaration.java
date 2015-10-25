package com.writeoncereadmany.minstrel.compile.ast.statements;

import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class FunctionDeclaration implements Statement
{
    public final Terminal name;
    public final FunctionExpression function;

    public FunctionDeclaration(Terminal name, FunctionExpression function)
    {
        this.name = name;
        this.function = function;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionDeclaration(this);
    }

}
