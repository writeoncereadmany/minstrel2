package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeEngine;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.Implementation;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ImplementationGuaranteed implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target, TypeEngine engine)
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
                                   : Stream.of(new TypeError("Target type cannot accept implementation " + impl)));
    }
}
