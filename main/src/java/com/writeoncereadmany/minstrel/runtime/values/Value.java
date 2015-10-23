package com.writeoncereadmany.minstrel.runtime.values;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;

import java.util.Collection;

public interface Value
{
    Value call(Interpreter interpreter, Value... arguments);

    default Value call(Interpreter interpreter, Collection<Value> arguments)
    {
        return call(interpreter, arguments.toArray(new Value[arguments.size()]));
    }

    Value get(String name);
}
