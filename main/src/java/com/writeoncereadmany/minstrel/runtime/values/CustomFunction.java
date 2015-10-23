package com.writeoncereadmany.minstrel.runtime.values;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Body;
import com.writeoncereadmany.minstrel.compile.ast.fragments.ParameterList;
import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;

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
    public Value call(Interpreter interpreter)
    {
        interpreter.enterNewScope();
        parameterList.visit(interpreter);

        interpreter.exitScope();
        return null;
    }
}
