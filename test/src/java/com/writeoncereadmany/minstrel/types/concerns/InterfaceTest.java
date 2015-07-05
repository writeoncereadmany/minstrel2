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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.writeoncereadmany.minstrel.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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

        // checker will only report each type mismatch once, so clear it before trying again:
        checker.clear();

        Type foo1 = new Type(new Interface(mapOf(entry("foo", stringToCat))));
        Type foo2 = new Type(new Interface(mapOf(entry("foo", stringToMammal))));

        assertThat(checker.canAssign(foo2, foo1), is(not(emptyStream())));
    }

    @Test
    public void canHandleRecursiveDefinitions()
    {
        ScopeIndex stringListDefinition = new ScopeIndex(5, 1);
        ScopeIndex handleNextDefn = new ScopeIndex(5, 2);
        ScopeIndex handleEndDefn = new ScopeIndex(5, 3);

        TypeDefinition stringList = new ConcreteTypeDefinition(stringListDefinition);
        TypeDefinition handleNext = new ConcreteTypeDefinition(handleNextDefn);
        TypeDefinition handleEnd = new ConcreteTypeDefinition(handleEndDefn);

        Interface iStringList = new Interface(mapOf(entry("onItem", handleNext), entry("onEnd", handleEnd)));

        Type stringListImpl = new Type(iStringList);
        Type handleNextImpl = new Type(new FunctionType(asList(STRING, stringList), STRING));
        Type handleEndImpl = new Type(new FunctionType(emptyList(), STRING));

        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(stringListDefinition, stringListImpl),
                entry(handleNextDefn, handleNextImpl),
                entry(handleEndDefn, handleEndImpl)
        ));

        assertThat(checker.canAssign(stringList, stringList), is(emptyStream()));
    }

    @Test
    public void canHandleMismatchedRecursiveDefinitions()
    {
        ScopeIndex stringListDefinition = new ScopeIndex(5, 1);
        ScopeIndex handleNextStringDefn = new ScopeIndex(5, 2);
        ScopeIndex handleEndStringDefn = new ScopeIndex(5, 3);

        ScopeIndex numberListDefinition = new ScopeIndex(5, 4);
        ScopeIndex handleNextNumberDefn = new ScopeIndex(5, 5);
        ScopeIndex handleEndNumberDefn = new ScopeIndex(5, 6);

        TypeDefinition stringList = new ConcreteTypeDefinition(stringListDefinition);
        TypeDefinition handleNextString = new ConcreteTypeDefinition(handleNextStringDefn);
        TypeDefinition handleEndString = new ConcreteTypeDefinition(handleEndStringDefn);

        TypeDefinition numberList = new ConcreteTypeDefinition(numberListDefinition);
        TypeDefinition handleNextNumber = new ConcreteTypeDefinition(handleNextNumberDefn);
        TypeDefinition handleEndNumber = new ConcreteTypeDefinition(handleEndNumberDefn);

        Interface iStringList = new Interface(mapOf(entry("onItem", handleNextString), entry("onEnd", handleEndString)));
        Interface iNumberList = new Interface(mapOf(entry("onItem", handleNextNumber), entry("onEnd", handleEndNumber)));

        Type stringListImpl = new Type(iStringList);
        Type handleNextStringImpl = new Type(new FunctionType(asList(STRING, stringList), STRING));
        Type handleEndStringImpl = new Type(new FunctionType(emptyList(), STRING));

        Type numberListImpl = new Type(iNumberList);
        Type handleNextNumberImpl = new Type(new FunctionType(asList(NUMBER, stringList), STRING));
        Type handleEndNumberImpl = new Type(new FunctionType(emptyList(), STRING));

        TypeChecker checker = new TypeChecker(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(NUMBER_DEFINITION, NUMBER_IMPL),
                entry(stringListDefinition, stringListImpl),
                entry(handleNextStringDefn, handleNextStringImpl),
                entry(handleEndStringDefn, handleEndStringImpl),
                entry(numberListDefinition, numberListImpl),
                entry(handleNextNumberDefn, handleNextNumberImpl),
                entry(handleEndNumberDefn, handleEndNumberImpl)
        ));

        assertThat(checker.canAssign(stringList, numberList), is(not(emptyStream())));
    }
}
