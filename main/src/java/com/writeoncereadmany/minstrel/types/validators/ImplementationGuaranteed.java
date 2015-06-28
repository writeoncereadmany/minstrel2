package com.writeoncereadmany.minstrel.types.validators;

import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeError;
import com.writeoncereadmany.minstrel.types.concerns.Implementation;

import java.util.Optional;
import java.util.stream.Stream;

public class ImplementationGuaranteed implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type target, Type source)
    {
        Optional<Implementation> targetImplementation = target.getConcern(Implementation.class);
        Optional<Implementation> sourceImplementation = source.getConcern(Implementation.class);

        return targetImplementation.map(targetImpl -> targetImpl.isSupersetOf(sourceImplementation)).orElse(Stream.empty());
    }
}
