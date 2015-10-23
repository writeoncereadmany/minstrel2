package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;

public class MultipliedByFunction extends Function
{
    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        Value augend = arguments[0];
        Value addend = arguments[1];

        return augend.get("multipliedBy").call(interpreter, addend);
    }
}
