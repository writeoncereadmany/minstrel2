package com.writeoncereadmany.minstrel.runtime.values.functions;

import com.writeoncereadmany.minstrel.runtime.interpreter.Interpreter;
import com.writeoncereadmany.minstrel.runtime.values.Value;
import com.writeoncereadmany.minstrel.runtime.values.primitives.MinstrelString;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

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
    public Value call(Interpreter interpreter, Value... arguments)
    {
        Value value = arguments[0];
        MinstrelString toPrint = (MinstrelString) value.get("show").call(interpreter);
        printStream.println(toPrint.getText());
        return success;
    }
}
