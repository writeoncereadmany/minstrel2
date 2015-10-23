package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.number.RationalNumber;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.functions.ConstantFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.Function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MinstrelNumber implements Value
{
    private final RationalNumber value;
    private final Map<String, Function> methods;

    public MinstrelNumber(RationalNumber value)
    {
        this.value = value;
        this.methods = createMethods();
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

    private Map<String, Function> createMethods()
    {
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("show", new ConstantFunction(new MinstrelString(value.toString())));
        methods.put("plus", new BinaryNumberOperation(RationalNumber::plus));
        methods.put("minus", new BinaryNumberOperation(RationalNumber::subtract));
        methods.put("multipliedBy", new BinaryNumberOperation(RationalNumber::multiply));
        methods.put("dividedBy", new BinaryNumberOperation(RationalNumber::divide));
        methods.put("negate", new UnaryNumberOperation(RationalNumber::negate));
        return methods;
    }

    private class BinaryNumberOperation extends Function
    {
        private final BiFunction<RationalNumber, RationalNumber, RationalNumber> func;

        public BinaryNumberOperation(BiFunction<RationalNumber, RationalNumber, RationalNumber> func)
        {
            this.func = func;
        }

        @Override
        public Value call(Interpreter interpreter, Value... arguments)
        {
            MinstrelNumber arg = (MinstrelNumber)arguments[0];
            return new MinstrelNumber(func.apply(value, arg.value()));
        }
    }

    private class UnaryNumberOperation extends Function
    {
        private final java.util.function.Function<RationalNumber, RationalNumber> func;

        public UnaryNumberOperation(java.util.function.Function<RationalNumber, RationalNumber> func)
        {
            this.func = func;
        }

        @Override
        public Value call(Interpreter interpreter, Value... arguments)
        {
            return new MinstrelNumber(func.apply(value));
        }
    }
}
