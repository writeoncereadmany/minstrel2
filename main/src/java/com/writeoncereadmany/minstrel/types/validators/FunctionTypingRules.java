package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.stream.Stream;

public class FunctionTypingRules implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target)
    {
        return Stream.empty();
    }
}
