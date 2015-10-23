package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelNumber;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

import static java.util.Collections.singletonList;

public class PlusFunction extends Function
{
    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        Value augend = arguments[0];
        Value addend = arguments[1];

        return augend.get("plus").call(interpreter, addend);
    }
}
