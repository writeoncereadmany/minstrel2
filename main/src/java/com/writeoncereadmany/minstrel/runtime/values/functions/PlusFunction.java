package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelNumber;

public class PlusFunction extends Function
{
    @Override
    public Value call(Interpreter interpreter)
    {
        MinstrelNumber augend = (MinstrelNumber)interpreter.nextArgument();
        MinstrelNumber addend = (MinstrelNumber)interpreter.nextArgument();
        return new MinstrelNumber(augend.value().plus(addend.value()));
    }
}
