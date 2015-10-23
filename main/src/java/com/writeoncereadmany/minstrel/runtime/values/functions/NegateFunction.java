package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;

public class NegateFunction extends Function
{
    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        Value augend = arguments[0];

        return augend.get("negate").call(interpreter);
    }
}
