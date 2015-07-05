package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
import com.writeoncereadmany.minstrel.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.types.validators.FunctionTypingRules;
import com.writeoncereadmany.minstrel.types.validators.ImplementationGuaranteed;
import org.junit.Test;

import java.util.Map;

import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.empty;
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

    private final TypeChecker typeChecker = new TypeChecker(asList(new ImplementationGuaranteed(), new FunctionTypingRules()), definitions);


    @Test
    public void aFunctionTypeIsASubtypeOfItself()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type sameFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));

        assertThat(typeChecker.canAssign(aFunction, sameFunction).collect(toList()), is(empty()));
    }

    @Test
    public void cannotAssignANonFunctionToAFunction()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type notAFunction = new Type();

        assertThat(typeChecker.canAssign(notAFunction, aFunction).collect(toList()), is(not(empty())));
    }

    @Test
    public void cannotAssignAFunctionToAFunctionWithDifferentArity()
    {
        Type oneArgFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type zeroArgFunction = new Type(new FunctionType(emptyList(), MAMMAL));

        assertThat(typeChecker.canAssign(oneArgFunction, zeroArgFunction).collect(toList()), is(not(empty())));
        assertThat(typeChecker.canAssign(zeroArgFunction, oneArgFunction).collect(toList()), is(not(empty())));
    }

    @Test
    public void cannotAssignFunctionToFunctionWithSubtypeReturnType()
    {
        Type returnsMammal = new Type(new FunctionType(emptyList(), MAMMAL));
        Type returnsAnimal = new Type(new FunctionType(emptyList(), ANIMAL));

        assertThat(typeChecker.canAssign(returnsAnimal, returnsMammal).collect(toList()), is(not(empty())));
    }

    @Test
    public void canAssignFunctionToFunctionWithSupertypeReturnType()
    {
        Type returnsMammal = new Type(new FunctionType(emptyList(), MAMMAL));
        Type returnsCat = new Type(new FunctionType(emptyList(), CAT));

        assertThat(typeChecker.canAssign(returnsCat, returnsMammal).collect(toList()), is(empty()));
    }

    @Test
    public void canAssignFunctionToFunctionWithSubtypeArguments()
    {
        Type takesCat = new Type(new FunctionType(asList(CAT, CAT), MAMMAL));
        Type takesAnimal = new Type(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));

        assertThat(typeChecker.canAssign(takesAnimal, takesCat).collect(toList()), is(empty()));
    }

    @Test
    public void cannotAssignFunctionToFunctionWithSupertypeArguments()
    {
        Type takesAnimal = new Type(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));
        Type takesCat = new Type(new FunctionType(asList(CAT, CAT), MAMMAL));

        assertThat(typeChecker.canAssign(takesCat, takesAnimal).collect(toList()), is(not(empty())));
    }
}
