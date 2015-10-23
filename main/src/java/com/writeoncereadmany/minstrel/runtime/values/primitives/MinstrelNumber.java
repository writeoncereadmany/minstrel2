package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.number.RationalNumber;
import com.writeoncereadmany.minstrel.runtime.values.functions.ConstantFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.Function;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MinstrelNumber implements Value
{
    private final RationalNumber value;
    private final Map<String, Function> methods;

    public MinstrelNumber(RationalNumber value)
    {
        this.value = value;
        this.methods = createMethods(value);
    }

    @Override
    public Value call(Interpreter interpreter) {
        throw new UnsupportedOperationException("Cannot call a number: not a function");
    }

    @Override
    public Value get(String name)
    {
        return methods.get(name);
    }

    public RationalNumber value()
    {
        return value;
    }

    private static Map<String, Function> createMethods(RationalNumber value)
    {
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("show", new ConstantFunction(new MinstrelString(value.toString())));
        return methods;
    }
}
