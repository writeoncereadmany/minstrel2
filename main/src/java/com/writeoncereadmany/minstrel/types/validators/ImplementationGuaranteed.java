package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.Implementation;

import java.util.Optional;
import java.util.stream.Stream;

public class ImplementationGuaranteed implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target)
    {
        Optional<Implementation> sourceImplementation = source.getConcern(Implementation.class);
        Optional<Implementation> targetImplementation = target.getConcern(Implementation.class);

        return targetImplementation.map(targetImpl -> targetImpl.isSupersetOf(sourceImplementation)).orElse(Stream.empty());
    }
}
