package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelNumber;

import java.util.Queue;

public class PlusFunction extends Function
{
    @Override
    public Value call(Interpreter interpreter)
    {
        Queue<Value> arguments = interpreter.getArguments();
        Value augend = arguments.poll();
        Value addend = arguments.poll();

        interpreter.setArguments(addend);
        return augend.get("plus").call(interpreter);
    }
}
