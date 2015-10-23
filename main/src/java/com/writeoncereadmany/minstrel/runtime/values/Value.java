package com.writeoncereadmany.minstrel.runtime.values;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;

import java.util.List;

public interface Value
{
    Value call(Interpreter interpreter, Value... arguments);

    Value get(String name);
}
