package com.writeoncereadmany.minstrel.compile.types.validators;

import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;

import java.util.stream.Stream;

public interface TypingRule
{
    Stream<TypeError> isAssignableTo(StructuralType source, StructuralType target, TypeEngine checker);
}
