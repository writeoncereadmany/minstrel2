package com.writeoncereadmany.minstrel.compile.ast.statements;

import com.writeoncereadmany.minstrel.builtins.Builtins;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.expressions.Expression;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class VariableDeclaration implements Typed, Statement
{
    public final TypeExpression type;
    public final Terminal name;
    public final Expression expression;

    public VariableDeclaration(TypeExpression type, Terminal name, Expression expression)
    {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitVariableDeclaration(this);
    }

    @Override
    public TypeDefinition type(TypeChecker checker)
    {
        return new ConcreteTypeDefinition(Builtins.SUCCESS_TYPE.scopeIndex());
    }
}
