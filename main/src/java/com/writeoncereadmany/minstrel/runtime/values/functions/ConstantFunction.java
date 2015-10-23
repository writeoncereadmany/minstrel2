package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;

public class ConstantFunction extends Function
{
    private final Value constant;

    public ConstantFunction(Value constant)
    {
        this.constant = constant;
    }

    @Override
    public final Value call(Interpreter interpreter)
    {
        return constant;
    }
}
