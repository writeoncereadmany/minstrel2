package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.number.RationalNumber;
import com.writeoncereadmany.minstrel.runtime.values.functions.ConstantFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.Function;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiFunction;

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
    public Value call(Interpreter interpreter, Value... arguments) {
        throw new UnsupportedOperationException("Cannot call a number: not a function");
    }

    @Override
    public Value get(String name)
    {
        return methods.computeIfAbsent(name, x -> {
            throw new UnsupportedOperationException("Member " + name + " missing");
        });
    }

    public RationalNumber value()
    {
        return value;
    }

    private static Map<String, Function> createMethods(RationalNumber value)
    {
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("show", new ConstantFunction(new MinstrelString(value.toString())));
        methods.put("plus", new BinaryNumberOperation(value, RationalNumber::plus));
        return methods;
    }

    private static class BinaryNumberOperation extends Function
    {
        private final RationalNumber value;
        private final BiFunction<RationalNumber, RationalNumber, RationalNumber> func;

        public BinaryNumberOperation(RationalNumber value,
                                     BiFunction<RationalNumber, RationalNumber, RationalNumber> func)
        {
            this.value = value;
            this.func = func;
        }

        @Override
        public Value call(Interpreter interpreter, Value... arguments)
        {
            MinstrelNumber arg = (MinstrelNumber)arguments[0];
            return new MinstrelNumber(func.apply(value, arg.value()));
        }
    }
}
