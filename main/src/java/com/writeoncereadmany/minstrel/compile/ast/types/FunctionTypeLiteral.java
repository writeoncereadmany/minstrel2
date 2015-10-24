package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.visitors.AstVisitor;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FunctionTypeLiteral implements TypeExpression
{
    private final TypeList argumentTypes;
    private final TypeExpression returnType;

    public FunctionTypeLiteral(TypeList argumentTypes, TypeExpression returnType)
    {
        this.argumentTypes = argumentTypes;
        this.returnType = returnType;
    }

    @Override
    public void visit(AstVisitor visitor)
    {
        visitor.visitFunctionTypeLiteral(argumentTypes, returnType);
    }

    @Override
    public TypeDefinition lookupType(NameResolver nameResolver)
    {
        List<TypeDefinition> args = argumentTypes.argumentTypes.stream().map(t -> t.lookupType(nameResolver)).collect(toList());
        return new FunctionType(args, returnType.lookupType(nameResolver));
    }
}
