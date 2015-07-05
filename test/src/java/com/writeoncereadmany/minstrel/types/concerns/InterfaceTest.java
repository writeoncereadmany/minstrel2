package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
import com.writeoncereadmany.minstrel.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.types.validators.ImplementationRule;
import com.writeoncereadmany.minstrel.types.validators.InterfaceRule;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.writeoncereadmany.minstrel.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class InterfaceTest
{
    private static final ScopeIndex NUMBER_DEFINITION = new ScopeIndex(1, 1);
    private static final ScopeIndex STRING_DEFINITION = new ScopeIndex(1, 2);
    private static final ScopeIndex FISH_DEFINITION = new ScopeIndex(3, 1);
    private static final ScopeIndex DOG_DEFINITION = new ScopeIndex(3, 2);
    private static final ScopeIndex CAT_DEFINITION = new ScopeIndex(3, 3);
    private static final ScopeIndex ANIMAL_DEFINITION = new ScopeIndex(3, 4);
    private static final ScopeIndex MAMMAL_DEFINITION = new ScopeIndex(3, 5);

    private static final TypeDefinition NUMBER = new ConcreteTypeDefinition(NUMBER_DEFINITION);
    private static final TypeDefinition STRING = new ConcreteTypeDefinition(STRING_DEFINITION);
    private static final TypeDefinition ANIMAL = new ConcreteTypeDefinition(ANIMAL_DEFINITION);
    private static final TypeDefinition MAMMAL = new ConcreteTypeDefinition(MAMMAL_DEFINITION);
    private static final TypeDefinition CAT = new ConcreteTypeDefinition(CAT_DEFINITION);

    private static final Type NUMBER_IMPL = new Type(new Implementation(NUMBER_DEFINITION));
    private static final Type STRING_IMPL = new Type(new Implementation(STRING_DEFINITION));
    private static final Type ANIMAL_IMPL = new Type(new Implementation(CAT_DEFINITION, DOG_DEFINITION, FISH_DEFINITION));
    private static final Type MAMMAL_IMPL = new Type(new Implementation(CAT_DEFINITION, DOG_DEFINITION));
    private static final Type CAT_IMPL = new Type(new Implementation(CAT_DEFINITION));

    private static final List<TypingRule> TYPING_RULES = asList(new ImplementationRule(), new FunctionRules(), new InterfaceRule());

    @Test
    public void anInterfaceIsAssignableToItself()
    {
        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        Type point2d = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));
        Type vector2d = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));

        assertThat(checker.canAssign(point2d, vector2d), is(emptyStream()));
    }

    @Test
    public void anInterfaceIsAssignableToATypeWhichProvidesASubsetOfOperations()
    {
        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        Type hasAnXAndAY = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));
        Type hasAnX = new Type(new Interface(mapOf(entry("x", NUMBER))));

        assertThat(checker.canAssign(hasAnXAndAY, hasAnX), is(emptyStream()));
    }

    @Test
    public void anInterfaceIsNotAssignableToATypeWhichProvidesMoreOperations()
    {
        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        Type hasAnX = new Type(new Interface(mapOf(entry("x", NUMBER))));
        Type hasAnXAndAY = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));

        assertThat(checker.canAssign(hasAnX, hasAnXAndAY), is(not(emptyStream())));
    }

    @Test
    public void anInterfaceIsAssignableIfFunctionTypesAreAssignable()
    {
        ScopeIndex stringToCatDefinition = new ScopeIndex(4, 1);
        ScopeIndex stringToMammalDefinition = new ScopeIndex(4, 2);

        TypeDefinition stringToCat = new ConcreteTypeDefinition(stringToCatDefinition);
        TypeDefinition stringToMammal = new ConcreteTypeDefinition(stringToMammalDefinition);

        Type stringToCatImpl = new Type(new FunctionType(asList(STRING), CAT));
        Type stringToMammalImpl = new Type(new FunctionType(asList(STRING), MAMMAL));

        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(CAT_DEFINITION, CAT_IMPL),
                entry(MAMMAL_DEFINITION, MAMMAL_IMPL),
                entry(stringToCatDefinition, stringToCatImpl),
                entry(stringToMammalDefinition, stringToMammalImpl)));

        // for clarity:
        assertThat(checker.canAssign(stringToCatImpl, stringToMammalImpl), is(emptyStream()));

        Type foo1 = new Type(new Interface(mapOf(entry("foo", stringToCat))));
        Type foo2 = new Type(new Interface(mapOf(entry("foo", stringToMammal))));

        assertThat(checker.canAssign(foo1, foo2), is(emptyStream()));
    }

    @Test
    public void anInterfaceIsNotAssignableIfFunctionTypesAreNotAssignable()
    {
        ScopeIndex stringToCatDefinition = new ScopeIndex(4, 1);
        ScopeIndex stringToMammalDefinition = new ScopeIndex(4, 2);

        TypeDefinition stringToCat = new ConcreteTypeDefinition(stringToCatDefinition);
        TypeDefinition stringToMammal = new ConcreteTypeDefinition(stringToMammalDefinition);

        Type stringToCatImpl = new Type(new FunctionType(asList(STRING), CAT));
        Type stringToMammalImpl = new Type(new FunctionType(asList(STRING), MAMMAL));

        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(CAT_DEFINITION, CAT_IMPL),
                entry(MAMMAL_DEFINITION, MAMMAL_IMPL),
                entry(stringToCatDefinition, stringToCatImpl),
                entry(stringToMammalDefinition, stringToMammalImpl)));

        // for clarity:
        assertThat(checker.canAssign(stringToMammalImpl,stringToCatImpl), is(not(emptyStream())));

        Type foo1 = new Type(new Interface(mapOf(entry("foo", stringToCat))));
        Type foo2 = new Type(new Interface(mapOf(entry("foo", stringToMammal))));

        assertThat(checker.canAssign(foo2, foo1), is(not(emptyStream())));
    }
}
