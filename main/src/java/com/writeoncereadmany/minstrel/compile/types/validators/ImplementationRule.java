package com.writeoncereadmany.minstrel.compile.types.validators;

import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.concerns.Implementation;

import java.util.stream.Stream;

public class ImplementationRule implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(StructuralType source, StructuralType target, TypeEngine checker)
    {
        Implementation sourceImplementation = source.getConcern(Implementation.class);
        Implementation targetImplementation = target.getConcern(Implementation.class);

        // We can go from a specified (set of) types to an open type
        if(targetImplementation == null)
        {
            return Stream.empty();
        }
        // but not the other way round
        if(sourceImplementation == null)
        {
            return Stream.of(new TypeError("Required: a type which can only be one of " + targetImplementation.possibleImplementations +
                                           ", provided: a type which does not guarantee implementations"));
        }
        // otherwise, every type the source could be must be a type the target can accept
        return sourceImplementation.possibleImplementations.stream()
                .flatMap(impl -> targetImplementation.possibleImplementations.contains(impl)
                                   ? Stream.empty()
                                   : Stream.of(new TypeError("Target type cannot accept implementation " + impl.name())));
    }
}
