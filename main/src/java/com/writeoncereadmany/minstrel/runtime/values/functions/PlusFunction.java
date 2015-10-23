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
        MinstrelNumber augend = (MinstrelNumber)arguments.poll();
        MinstrelNumber addend = (MinstrelNumber)arguments.poll();
        return new MinstrelNumber(augend.value().plus(addend.value()));
    }
}
