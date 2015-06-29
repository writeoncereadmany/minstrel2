package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface TypingRule
{
    Stream<TypeError> isAssignableTo(Type source, Type target, Function<ScopeIndex, Type> provider, List<TypingRule> rules);
}
