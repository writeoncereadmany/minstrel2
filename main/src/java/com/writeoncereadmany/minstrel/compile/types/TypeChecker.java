package com.writeoncereadmany.minstrel.compile.types;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.TypingRule;
import com.writeoncereadmany.util.Multimap;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class TypeChecker
{
    private final List<TypingRule> rules;
    private final Map<ScopeIndex, Type> types;
    private final Multimap<TypeDefinition, TypeDefinition> alreadyVisitedDefinitions = new Multimap<>();

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
        if(alreadyVisitedDefinitions.contains(sourceTypeDefinition, targetTypeDefinition))
        {
            return Stream.empty();
        }
        alreadyVisitedDefinitions.put(sourceTypeDefinition, targetTypeDefinition);
        return canAssign(sourceTypeDefinition.getType(this), targetTypeDefinition.getType(this));
    }

    public void clear()
    {
        alreadyVisitedDefinitions.clear();
    }
}
