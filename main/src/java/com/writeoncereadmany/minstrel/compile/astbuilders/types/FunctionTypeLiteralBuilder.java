package com.writeoncereadmany.minstrel.compile.astbuilders.types;


import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.fragments.TypeList;
import com.writeoncereadmany.minstrel.compile.ast.types.FunctionTypeLiteral;
import com.writeoncereadmany.minstrel.compile.ast.types.TypeExpression;
import com.writeoncereadmany.minstrel.compile.astbuilders.base.TwoNodeBuilder;

public class FunctionTypeLiteralBuilder extends TwoNodeBuilder<TypeList, TypeExpression, FunctionTypeLiteral>
{
    public FunctionTypeLiteralBuilder(Source source)
    {
        super(source, FunctionTypeLiteral::new, TypeList.class, TypeExpression.class);
    }
}
