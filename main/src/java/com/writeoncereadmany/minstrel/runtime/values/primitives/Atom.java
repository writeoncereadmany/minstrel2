package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.ConstantFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.Function;

import java.util.HashMap;
import java.util.Map;

public class Atom implements Value
{
    private final Map<String, Function> methods;

    public Atom(String name)
    {
        methods = new HashMap<>();
        methods.put("show", new ConstantFunction(new MinstrelString(name)));
    }

    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        throw new UnsupportedOperationException("An atom is not a function");
    }

    @Override
    public Value get(String name)
    {
        return methods.get(name);
    }
}
