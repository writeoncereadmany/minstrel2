package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class MemberAccess implements Expression
{
    private final Expression expression;
    private final Terminal memberName;

    public MemberAccess(Expression expression, Terminal memberName)
    {
        this.expression = expression;
        this.memberName = memberName;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitMemberAccess(expression, memberName);    
    }

}
