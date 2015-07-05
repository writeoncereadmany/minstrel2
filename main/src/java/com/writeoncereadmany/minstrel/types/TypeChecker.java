package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TypeChecker
{
    private final List<TypingRule> rules;
    private final Map<ScopeIndex, Type> types;

    public TypeChecker(List<TypingRule> rules, Map<ScopeIndex, Type> types)
    {
        this.rules = rules;
        this.types = types;
    }

    public Type lookupType(ScopeIndex index)
    {
        return types.computeIfAbsent(index, s -> { throw new IllegalArgumentException("Scope index " + s + " not defined"); });
    }

    public Stream<TypeError> canAssign(Type sourceType, Type targetType)
    {
        return rules.stream().flatMap(rule -> rule.isAssignableTo(sourceType, targetType, this));
    }

    public Stream<TypeError> canAssign(TypeDefinition sourceTypeDefinition, TypeDefinition targetTypeDefinition)
    {
        return canAssign(sourceTypeDefinition.getType(this), targetTypeDefinition.getType(this));
    }
}
