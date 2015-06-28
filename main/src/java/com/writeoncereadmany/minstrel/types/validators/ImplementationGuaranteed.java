package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.Implementation;

import java.util.Optional;
import java.util.stream.Stream;

public class ImplementationGuaranteed implements TypingRule
{
    @Override
    public Stream<TypeError> isSubtypeOf(Type supertype, Type subtype)
    {
        Optional<Implementation> ofSupertype = supertype.getConcern(Implementation.class);
        Optional<Implementation> ofSubtype = subtype.getConcern(Implementation.class);

        if(!ofSupertype.isPresent())
        {
            return Stream.empty();
        }
        return ofSupertype.map(i -> i.isSubtype(ofSubtype)).orElse(Stream.empty());
    }
}
