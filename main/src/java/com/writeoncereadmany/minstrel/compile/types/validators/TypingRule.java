package com.writeoncereadmany.minstrel.compile.types.validators;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.TypeError;

import java.util.stream.Stream;

public interface TypingRule
{
    Stream<TypeError> isAssignableTo(Type source, Type target, TypeChecker checker);
}
