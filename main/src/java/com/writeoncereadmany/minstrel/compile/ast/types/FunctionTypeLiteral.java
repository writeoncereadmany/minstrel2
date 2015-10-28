package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FunctionTypeLiteral implements TypeExpression
{
    private final Source source;
    public final TypeList parameters;
    public final TypeExpression returnType;

    public FunctionTypeLiteral(Source source, TypeList parameters, TypeExpression returnType)
    {
        this.source = source;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionTypeLiteral(this);
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public TypeDefinition type()
    {
        List<TypeDefinition> args = parameters.types.stream().map(t -> t.type()).collect(toList());
        return new FunctionType(args, returnType.type());
    }
}
