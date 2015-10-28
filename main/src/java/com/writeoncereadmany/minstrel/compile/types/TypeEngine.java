package com.writeoncereadmany.minstrel.compile.types;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.concerns.IncoherentType;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.TypingRule;
import com.writeoncereadmany.util.Multimap;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TypeEngine
{
    private final List<TypingRule> rules;
    private final Map<ScopeIndex, Type> typeDefinitions;
    private final Multimap<TypeDefinition, TypeDefinition> alreadyVisitedDefinitions = new Multimap<>();

    public TypeEngine(List<TypingRule> rules, Map<ScopeIndex, Type> typeDefinitions)
    {
        this.rules = rules;
        this.typeDefinitions = typeDefinitions;
    }

    public Type lookupNamedType(ScopeIndex index)
    {
        return typeDefinitions.computeIfAbsent(index, s -> {
            throw new IllegalArgumentException("Scope index " + s + " not defined");
        });
    }

    public Stream<TypeError> canAssign(Type sourceType, Type targetType)
    {
        if(sourceType instanceof Nothing)
        {
            // Nothing is assignable to anything: including Nothing
            return Stream.empty();
        }
        else if(targetType instanceof Nothing)
        {
            // There are no instances that satisfy Nothing, so no non-Nothing values can be assigned to it
            return Stream.of(new TypeError("Cannot assign to something of type Nothing"));
        }
        else
        {
            StructuralType sourceStructuralType = (StructuralType)sourceType;
            StructuralType targetStructuralType = (StructuralType)targetType;
            return rules.stream().flatMap(rule -> rule.isAssignableTo(sourceStructuralType, targetStructuralType, this));
        }
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

    public Stream<TypeError> checkCoherent(TypeDefinition definition)
    {
        final Type type = definition.getType(this);
        if(type instanceof StructuralType)
        {
            final IncoherentType typeIncoherency = ((StructuralType)type).getConcern(IncoherentType.class);
            if(typeIncoherency != null)
            {
                return Stream.of(new TypeError(typeIncoherency.reason));
            }
            // and we're coherent, or...
        }
        // ... we're Nothing, which is coherent.
        return Stream.empty();
    }
}
