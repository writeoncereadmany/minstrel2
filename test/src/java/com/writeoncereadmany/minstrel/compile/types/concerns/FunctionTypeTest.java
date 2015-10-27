package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import org.junit.Test;

import java.util.Map;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
            entry(CAT_DEF, new StructuralType(new Implementation(CAT_DEF))),
            entry(MAMMAL_DEF, new StructuralType(new Implementation(CAT_DEF, DOG_DEF))),
            entry(ANIMAL_DEF, new StructuralType(new Implementation(CAT_DEF, DOG_DEF, FISH_DEF))));

    private final TypeDefinition ANIMAL = new ConcreteTypeDefinition(ANIMAL_DEF);
    private final TypeDefinition MAMMAL = new ConcreteTypeDefinition(MAMMAL_DEF);
    private final TypeDefinition CAT = new ConcreteTypeDefinition(CAT_DEF);

    private final TypeEngine typeEngine = new TypeEngine(asList(new ImplementationRule(), new FunctionRules()), definitions);


    @Test
    public void aFunctionTypeIsASubtypeOfItself()
    {
        StructuralType aFunction = new StructuralType(new FunctionType(singletonList(MAMMAL), MAMMAL));
        StructuralType sameFunction = new StructuralType(new FunctionType(singletonList(MAMMAL), MAMMAL));

        assertAssignable(aFunction, sameFunction);
    }

    @Test
    public void cannotAssignANonFunctionToAFunction()
    {
        StructuralType aFunction = new StructuralType(new FunctionType(singletonList(MAMMAL), MAMMAL));
        StructuralType notAFunction = new StructuralType();

        assertNotAssignable(notAFunction, aFunction);
    }

    @Test
    public void cannotAssignAFunctionToAFunctionWithDifferentArity()
    {
        StructuralType oneArgFunction = new StructuralType(new FunctionType(singletonList(MAMMAL), MAMMAL));
        StructuralType zeroArgFunction = new StructuralType(new FunctionType(emptyList(), MAMMAL));

        assertNotAssignable(oneArgFunction, zeroArgFunction);
        assertNotAssignable(zeroArgFunction, oneArgFunction);
    }

    @Test
    public void cannotAssignFunctionToFunctionWithSubtypeReturnType()
    {
        StructuralType returnsMammal = new StructuralType(new FunctionType(emptyList(), MAMMAL));
        StructuralType returnsAnimal = new StructuralType(new FunctionType(emptyList(), ANIMAL));

        assertNotAssignable(returnsAnimal, returnsMammal);
    }


    @Test
    public void canAssignFunctionToFunctionWithSupertypeReturnType()
    {
        StructuralType returnsMammal = new StructuralType(new FunctionType(emptyList(), MAMMAL));
        StructuralType returnsCat = new StructuralType(new FunctionType(emptyList(), CAT));

        assertAssignable(returnsCat, returnsMammal);
    }

    @Test
    public void canAssignFunctionToFunctionWithSubtypeArguments()
    {
        StructuralType takesCat = new StructuralType(new FunctionType(asList(CAT, CAT), MAMMAL));
        StructuralType takesAnimal = new StructuralType(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));

        assertAssignable(takesAnimal, takesCat);
    }


    @Test
    public void cannotAssignFunctionToFunctionWithSupertypeArguments()
    {
        StructuralType takesAnimal = new StructuralType(new FunctionType(asList(ANIMAL, ANIMAL), MAMMAL));
        StructuralType takesCat = new StructuralType(new FunctionType(asList(CAT, CAT), MAMMAL));

        assertNotAssignable(takesCat, takesAnimal);
    }

    private void assertAssignable(StructuralType source, StructuralType target)
    {
        assertThat(typeEngine.canAssign(source, target), is(emptyStream()));
    }

    private void assertNotAssignable(StructuralType source, StructuralType target)
    {
        assertThat(typeEngine.canAssign(source, target), is(not(emptyStream())));
    }

}
