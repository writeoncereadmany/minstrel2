package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class FunctionTypeTest
{
    private final ScopeIndex CAT_DEF = new ScopeIndex(3, 2);
    private final ScopeIndex DOG_DEF = new ScopeIndex(3, 3);
    private final ScopeIndex MAMMAL_DEF = new ScopeIndex(3, 4);
    private final ScopeIndex FISH_DEF = new ScopeIndex(3, 5);
    private final ScopeIndex ANIMAL_DEF = new ScopeIndex(3, 6);

    private final Map<ScopeIndex, Type> definitions = mapOf(
            entry(CAT_DEF, new Type(new Implementation(CAT_DEF))),
            entry(MAMMAL_DEF, new Type(new Implementation(CAT_DEF, DOG_DEF))),
            entry(ANIMAL_DEF, new Type(new Implementation(CAT_DEF, DOG_DEF, FISH_DEF))));

    private final TypeDefinition ANIMAL = new ConcreteTypeDefinition(ANIMAL_DEF);
    private final TypeDefinition MAMMAL = new ConcreteTypeDefinition(MAMMAL_DEF);
    private final TypeDefinition CAT = new ConcreteTypeDefinition(CAT_DEF);

    private final TypeChecker typeChecker = new TypeChecker(asList(new ImplementationRule(), new FunctionRules()), definitions, emptyMap());


    @Test
    public void aFunctionTypeIsASubtypeOfItself()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type sameFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));

        assertAssignable(aFunction, sameFunction);
    }

    @Test
    public void cannotAssignANonFunctionToAFunction()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type notAFunction = new Type();

        assertNotAssignable(notAFunction, aFunction);
    }

    @Test
    public void cannotAssignAFunctionToAFunctionWithDifferentArity()
    {
        Type oneArgFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type zeroArgFunction = new Type(new FunctionType(emptyList(), MAMMAL));

        assertNotAssignable(oneArgFunction, zeroArgFunction);
        assertNotAssignable(zeroArgFunction, oneArgFunction);
    }

    @Test
    public void cannotAssignFunctionToFunctionWithSubtypeReturnType()
    {
        Type returnsMammal = new Type(new FunctionType(emptyList(), MAMMAL));
        Type returnsAnimal = new Type(new FunctionType(emptyList(), ANIMAL));

        assertNotAssignable(returnsAnimal, returnsMammal);
    }


    @Test
    public void canAssignFunctionToFunctionWithSupertypeReturnType()
    {
        Type returnsMammal = new Type(new FunctionType(emptyList(), MAMMAL));
        Type returnsCat = new Type(new FunctionType(emptyList(), CAT));

        assertAssignable(returnsCat, returnsMammal);
    }

    @Test
    public void canAssignFunctionToFunctionWithSubtypeArguments()
    {
        Type takesCat = new Type(new FunctionType(asList(CAT, CAT), MAMMAL));
        Type takesAnimal = new Type(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));

        assertAssignable(takesAnimal, takesCat);
    }


    @Test
    public void cannotAssignFunctionToFunctionWithSupertypeArguments()
    {
        Type takesAnimal = new Type(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));
        Type takesCat = new Type(new FunctionType(asList(CAT, CAT), MAMMAL));

        assertNotAssignable(takesCat, takesAnimal);
    }

    private void assertAssignable(Type source, Type target)
    {
        assertThat(typeChecker.canAssign(source, target), is(emptyStream()));
    }

    private void assertNotAssignable(Type source, Type target)
    {
        assertThat(typeChecker.canAssign(source, target), is(not(emptyStream())));
    }

}
