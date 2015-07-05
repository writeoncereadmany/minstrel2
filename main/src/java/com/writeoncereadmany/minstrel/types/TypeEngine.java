package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Map;

public class TypeEngine
{
    private final List<TypingRule> rules;
    private final Map<ScopeIndex, Type> types;

    public TypeEngine(List<TypingRule> rules, Map<ScopeIndex, Type> types)
    {
        this.rules = rules;
        this.types = types;
    }

    public List<TypingRule> getRules()
    {
        return rules;
    }

    public Type lookupType(ScopeIndex index)
    {
        return types.computeIfAbsent(index, s -> { throw new IllegalArgumentException("Scope index " + s + " not defined"); });
    }
}
