package com.writeoncereadmany.minstrel.compile.types.validators;

import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.TypeError;
import com.writeoncereadmany.minstrel.compile.types.concerns.Interface;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;

import java.util.stream.Stream;

public class InterfaceRule implements TypingRule
{
    @Override
    public Stream<TypeError> isAssignableTo(Type source, Type target, TypeChecker engine)
    {
        Interface sourceInterface = source.getConcern(Interface.class);
        Interface targetInterface = target.getConcern(Interface.class);

        if(targetInterface == null)
        {
            return Stream.empty();
        }
        if(sourceInterface == null)
        {
            return Stream.of(new TypeError("Target defines an interface, and source doesn't"));
        }

        return targetInterface.members.keySet().stream().flatMap(member ->
        {
            TypeDefinition sourceType = sourceInterface.members.get(member);
            TypeDefinition targetType = targetInterface.members.get(member);
            if(sourceType == null)
            {
                return Stream.of(new TypeError("Source type does not have member " + member));
            }
            return engine.canAssign(sourceType, targetType);
        });
    }
}
