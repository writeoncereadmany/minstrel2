package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Function;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MinstrelString implements Value
{
    private final String text;
    private final Map<String, Function> methods;

    public MinstrelString(String text)
    {
        this.text = text;
        this.methods = createMethods(text);
    }

    @Override
    public Value call(Interpreter interpreter) {
        throw new UnsupportedOperationException("Cannot call a string: not a function");
    }

    @Override
    public Value get(String name)
    {
        return methods.get(name);
    }

    public String getText()
    {
        return text;
    }

    private static Map<String, Function> createMethods(String text)
    {
        return new HashMap<>();
    }
}
