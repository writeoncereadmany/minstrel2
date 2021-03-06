package com.writeoncereadmany.minstrel.compile.ast.expressions;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import static java.util.stream.Collectors.toList;

public class FunctionExpression implements Expression
{
    private final Source source;
    public final ParameterList parameterList;
    public final Body body;

    public FunctionExpression(Source source, ParameterList parameterList, Body body)
    {
        this.source = source;
        this.parameterList = parameterList;
        this.body = body;
    }

    @Override
    public void visit(AstVisitor visitor) 
    {
        visitor.visitFunctionExpression(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        return new FunctionType(parameterList.parameters.stream().map(p -> p.type).map(Typed::type).collect(toList()),
                                body.type());
    }
}
