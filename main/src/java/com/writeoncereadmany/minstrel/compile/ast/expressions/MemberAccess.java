package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class MemberAccess implements Expression
{
    public final Expression expression;
    public final Terminal memberName;

    public MemberAccess(Expression expression, Terminal memberName)
    {
        this.expression = expression;
        this.memberName = memberName;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitMemberAccess(this);
    }

    @Override
    public TypeDefinition type(TypeChecker checker)
    {
        return expression.type(checker).getMember(checker, memberName.text);
    }
}
