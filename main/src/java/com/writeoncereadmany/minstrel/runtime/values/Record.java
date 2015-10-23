package com.writeoncereadmany.minstrel.runtime.values;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;

import java.util.Map;

public class Record implements Value
{
    private final Map<String, Value> fields;

    public Record(Map<String, Value> fields)
    {
        this.fields = fields;
    }

    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        throw new UnsupportedOperationException("Cannot call a non-function");
    }

    @Override
    public Value get(String name)
    {
        return fields.get(name);
    }
}
