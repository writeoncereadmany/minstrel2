package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelString;

import java.io.PrintStream;

public class PrintFunction extends Function
{
    private final PrintStream printStream;
    private final Value success;

    public PrintFunction(PrintStream printStream, Value success)
    {
        this.printStream = printStream;
        this.success = success;
    }

    @Override
    public Value call(Interpreter interpreter)
    {
        Value value = interpreter.nextArgument();
        MinstrelString toPrint = (MinstrelString) value.get("show").call(interpreter);
        printStream.print(toPrint.getText());
        return success;
    }
}
