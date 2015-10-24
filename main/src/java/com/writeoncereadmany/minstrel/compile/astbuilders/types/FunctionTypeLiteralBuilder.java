package com.writeoncereadmany.minstrel.compile.astbuilders.types;


import com.writeoncereadmany.minstrel.compile.ast.expressions.Function;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionTypeLiteralBuilder extends TwoNodeBuilder<TypeList, TypeExpression, FunctionTypeLiteral>
{
    public FunctionTypeLiteralBuilder()
    {
        super(FunctionTypeLiteral::new, TypeList.class, TypeExpression.class);
    }
}
