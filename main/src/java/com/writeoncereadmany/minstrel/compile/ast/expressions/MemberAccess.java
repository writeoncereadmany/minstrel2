package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

public class MemberAccess implements Expression
{
    private final Source source;
    public final Expression expression;
    public final Terminal memberName;

    public MemberAccess(Source source, Expression expression, Terminal memberName)
    {
        this.source = source;
        this.expression = expression;
        this.memberName = memberName;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitMemberAccess(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return expression.type().getMember(memberName.text);
    }
}
