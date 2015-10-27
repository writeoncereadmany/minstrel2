package com.writeoncereadmany.minstrel.compile.ast.types;

import com.writeoncereadmany.minstrel.compile.ast.Typed;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.UndefinedType;

public class IndeterminateType implements Typed
{
    private final String reason;

    public IndeterminateType(String reason)
    {
        this.reason = reason;
    }

    @Override
    public TypeDefinition type()
    {
        return new UndefinedType(reason);
    }
}
