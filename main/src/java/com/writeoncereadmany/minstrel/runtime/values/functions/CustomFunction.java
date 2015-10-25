package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.compile.ast.expressions.FunctionExpression;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import static java.util.Arrays.asList;

public class CustomFunction extends Function
{
    private final Environment boundEnvironment;
    private final FunctionExpression function;

    public CustomFunction(Environment boundEnvironment, FunctionExpression function)
    {
        this.boundEnvironment = boundEnvironment;
        this.function = function;
    }

    @Override
    public Value call(Interpreter interpreter, Value... arguments)
    {
        interpreter.enterScope(boundEnvironment.createChild());
        interpreter.populateArguments(function.parameterList, asList(arguments));
        function.body.visit(interpreter);
        interpreter.exitScope();
        return interpreter.consume();
    }
}
