package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.stream.Stream;

public interface TypingRule
{
    Stream<TypeError> isAssignableTo(Type source, Type target, TypeChecker checker);
}
