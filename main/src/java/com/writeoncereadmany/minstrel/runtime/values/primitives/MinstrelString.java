package com.writeoncereadmany.minstrel.runtime.values.primitives;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.functions.ConstantFunction;
import com.writeoncereadmany.minstrel.runtime.values.functions.Function;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinstrelString implements Value
{
    private static final Pattern STRING_LITERAL = Pattern.compile("\"([^\"]*)\"");
    private final String text;
    private final Map<String, Function> methods;

    public MinstrelString(String text)
    {
        this.text = text;
        this.methods = createMethods(this);
    }

    public static MinstrelString fromLiteral(String textLiteral)
    {
        return new MinstrelString(stripQuotes(textLiteral));
    }

    private static String stripQuotes(String text)
    {
        Matcher matcher = STRING_LITERAL.matcher(text);
        if(matcher.find())
        {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Not a well-formatted string literal");
    }

    @Override
    public Value call(Interpreter interpreter, Value... arguments) {
        throw new UnsupportedOperationException("Cannot call a string: not a function");
    }

    @Override
    public Value get(String name)
    {
        return methods.computeIfAbsent(name, x -> { throw new UnsupportedOperationException("Member " + name + " missing"); });
    }

    public String getText()
    {
        return text;
    }

    private Map<String, Function> createMethods(MinstrelString minstrelString)
    {
        HashMap<String, Function> methods = new HashMap<>();
        methods.put("show", new ConstantFunction(minstrelString));
        return methods;
    }
}
