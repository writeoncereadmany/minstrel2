package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.types.validators.FunctionTypingRules;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
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

    private final List<TypingRule> FUNCTION_TYPING_RULES = singletonList(new FunctionTypingRules());

    @Before
    public void setUp()
    {

    }


    @Test
    public void aFunctionTypeIsASubtypeOfItself()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type sameFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));

        assertThat(aFunction.isAssignableTo(sameFunction, FUNCTION_TYPING_RULES, definitions::get).collect(toList()), is(empty()));
    }

    @Test
    public void cannotAssignANonFunctionToAFunction()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type notAFunction = new Type();

        assertThat(notAFunction.isAssignableTo(aFunction, FUNCTION_TYPING_RULES, definitions::get).collect(toList()), is(not(empty())));
    }

    @Test
    public void cannotAssignAFunctionToAFunctionWithDifferentArity()
    {
        Type oneArgFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type zeroArgFunction = new Type(new FunctionType(emptyList(), MAMMAL));

        assertThat(oneArgFunction.isAssignableTo(zeroArgFunction, FUNCTION_TYPING_RULES, definitions::get).collect(toList()), is(not(empty())));
        assertThat(zeroArgFunction.isAssignableTo(oneArgFunction, FUNCTION_TYPING_RULES, definitions::get).collect(toList()), is(not(empty())));
    }
}
