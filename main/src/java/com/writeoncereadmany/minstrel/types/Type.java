package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.types.concerns.Concern;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Optional;

public interface Type
{
    List<TypeError> isSubtypeOf(Type otherType, List<TypingRule> typingRules);

    <T extends Concern> Optional<T> getConcern(Class<T> concernType);
}
