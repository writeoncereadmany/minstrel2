package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.stream.Stream;

public interface TypingRule
{
    Stream<TypeError> isSubtypeOf(Type supertype, Type subtype);
}
