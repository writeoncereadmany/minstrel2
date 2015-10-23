package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class CustomFunction extends Function
{
    private final ParameterList parameterList;
    private final Body body;

    public CustomFunction(ParameterList parameterList, Body body)
    {
        this.parameterList = parameterList;
        this.body = body;
    }

    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        interpreter.enterNewScope();
        interpreter.populateArguments(parameterList, asList(arguments));
        body.visit(interpreter);
        interpreter.exitScope();
        return interpreter.consume();
    }
}
