package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import com.writeoncereadmany.minstrel.compile.types.validators.InterfaceRule;
import com.writeoncereadmany.minstrel.compile.types.validators.TypingRule;
import org.junit.Test;

import java.util.List;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class InterfaceTest
{
    private static final ScopeIndex NUMBER_DEFINITION = new ScopeIndex("Number", 1, 1);
    private static final ScopeIndex STRING_DEFINITION = new ScopeIndex("String", 1, 2);
    private static final ScopeIndex FISH_DEFINITION = new ScopeIndex("Fish", 3, 1);
    private static final ScopeIndex DOG_DEFINITION = new ScopeIndex("Dog", 3, 2);
    private static final ScopeIndex CAT_DEFINITION = new ScopeIndex("Cat", 3, 3);
    private static final ScopeIndex ANIMAL_DEFINITION = new ScopeIndex("Animal", 3, 4);
    private static final ScopeIndex MAMMAL_DEFINITION = new ScopeIndex("Mammal", 3, 5);

    private static final TypeDefinition NUMBER = new ConcreteTypeDefinition(NUMBER_DEFINITION, "INumber");
    private static final TypeDefinition STRING = new ConcreteTypeDefinition(STRING_DEFINITION, "IString");
    private static final TypeDefinition ANIMAL = new ConcreteTypeDefinition(ANIMAL_DEFINITION, "Animal");
    private static final TypeDefinition MAMMAL = new ConcreteTypeDefinition(MAMMAL_DEFINITION, "Mammal");
    private static final TypeDefinition CAT = new ConcreteTypeDefinition(CAT_DEFINITION, "Cat");

    private static final Type NUMBER_IMPL = new StructuralType(new Implementation(NUMBER_DEFINITION));
    private static final Type STRING_IMPL = new StructuralType(new Implementation(STRING_DEFINITION));
    private static final Type ANIMAL_IMPL = new StructuralType(new Implementation(CAT_DEFINITION, DOG_DEFINITION, FISH_DEFINITION));
    private static final Type MAMMAL_IMPL = new StructuralType(new Implementation(CAT_DEFINITION, DOG_DEFINITION));
    private static final Type CAT_IMPL = new StructuralType(new Implementation(CAT_DEFINITION));

    private static final List<TypingRule> TYPING_RULES = asList(new ImplementationRule(), new FunctionRules(), new InterfaceRule());

    @Test
    public void anInterfaceIsAssignableToItself()
    {
        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        StructuralType point2d = new StructuralType(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER)), "Point"));
        StructuralType vector2d = new StructuralType(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER)), "Vector"));

        assertAssignable(checker, point2d, vector2d);
    }

    @Test
    public void anInterfaceIsAssignableToATypeWhichProvidesASubsetOfOperations()
    {
        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        StructuralType hasAnXAndAY = new StructuralType(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER)), "XY"));
        StructuralType hasAnX = new StructuralType(new Interface(mapOf(entry("x", NUMBER)), "X"));

        assertAssignable(checker, hasAnXAndAY, hasAnX);
    }

    @Test
    public void anInterfaceIsNotAssignableToATypeWhichProvidesMoreOperations()
    {
        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(entry(NUMBER_DEFINITION, NUMBER_IMPL)));

        StructuralType hasAnX = new StructuralType(new Interface(mapOf(entry("x", NUMBER)), "X"));
        StructuralType hasAnXAndAY = new StructuralType(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER)), "XY"));

        assertNotAssignable(checker, hasAnX, hasAnXAndAY);
    }

    @Test
    public void anInterfaceIsAssignableIfFunctionTypesAreAssignable()
    {
        ScopeIndex stringToCatDefinition = new ScopeIndex("StringToCat", 4, 1);
        ScopeIndex stringToMammalDefinition = new ScopeIndex("StringToMammal", 4, 2);

        TypeDefinition stringToCat = new ConcreteTypeDefinition(stringToCatDefinition, "StringToCat");
        TypeDefinition stringToMammal = new ConcreteTypeDefinition(stringToMammalDefinition, "StringToMammal");

        StructuralType stringToCatImpl = new StructuralType(new FunctionType(asList(STRING), CAT));
        StructuralType stringToMammalImpl = new StructuralType(new FunctionType(asList(STRING), MAMMAL));

        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(CAT_DEFINITION, CAT_IMPL),
                entry(MAMMAL_DEFINITION, MAMMAL_IMPL),
                entry(stringToCatDefinition, stringToCatImpl),
                entry(stringToMammalDefinition, stringToMammalImpl)));

        // for clarity:
        assertAssignable(checker, stringToCatImpl, stringToMammalImpl);

        StructuralType foo1 = new StructuralType(new Interface(mapOf(entry("foo", stringToCat)), "CatMaker"));
        StructuralType foo2 = new StructuralType(new Interface(mapOf(entry("foo", stringToMammal)), "MammalMaker"));

        assertAssignable(checker, foo1, foo2);
    }


    @Test
    public void anInterfaceIsNotAssignableIfFunctionTypesAreNotAssignable()
    {
        ScopeIndex stringToCatDefinition = new ScopeIndex("StringToCat", 4, 1);
        ScopeIndex stringToMammalDefinition = new ScopeIndex("StringToMammal", 4, 2);

        TypeDefinition stringToCat = new ConcreteTypeDefinition(stringToCatDefinition, "StringToCat");
        TypeDefinition stringToMammal = new ConcreteTypeDefinition(stringToMammalDefinition, "StringToMammal");

        StructuralType stringToCatImpl = new StructuralType(new FunctionType(asList(STRING), CAT));
        StructuralType stringToMammalImpl = new StructuralType(new FunctionType(asList(STRING), MAMMAL));

        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(CAT_DEFINITION, CAT_IMPL),
                entry(MAMMAL_DEFINITION, MAMMAL_IMPL),
                entry(stringToCatDefinition, stringToCatImpl),
                entry(stringToMammalDefinition, stringToMammalImpl)));

        // for clarity:
        assertNotAssignable(checker, stringToMammalImpl, stringToCatImpl);

        // checker will only report each type mismatch once, so clear it before trying again:
        checker.clear();

        StructuralType catMaker = new StructuralType(new Interface(mapOf(entry("foo", stringToCat)), "CatMaker"));
        StructuralType mammalMaker = new StructuralType(new Interface(mapOf(entry("foo", stringToMammal)), "MammalMaker"));

        assertNotAssignable(checker, mammalMaker, catMaker);
    }


    @Test
    public void canHandleRecursiveDefinitions()
    {
        ScopeIndex stringListDefinition = new ScopeIndex("StringList", 5, 1);
        ScopeIndex handleNextDefn = new ScopeIndex("handleNext", 5, 2);
        ScopeIndex handleEndDefn = new ScopeIndex("handleEnd", 5, 3);

        TypeDefinition stringList = new ConcreteTypeDefinition(stringListDefinition, "StringList");
        TypeDefinition handleNext = new ConcreteTypeDefinition(handleNextDefn, "HandleNext");
        TypeDefinition handleEnd = new ConcreteTypeDefinition(handleEndDefn, "HandleEnd");

        Interface iStringList = new Interface(mapOf(entry("onItem", handleNext), entry("onEnd", handleEnd)), "StringReader");

        Type stringListImpl = new StructuralType(iStringList);
        Type handleNextImpl = new StructuralType(new FunctionType(asList(STRING, stringList), STRING));
        Type handleEndImpl = new StructuralType(new FunctionType(emptyList(), STRING));

        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(stringListDefinition, stringListImpl),
                entry(handleNextDefn, handleNextImpl),
                entry(handleEndDefn, handleEndImpl)
        ));

        assertAssignable(checker, stringList, stringList);
    }

    @Test
    public void canHandleMismatchedRecursiveDefinitions()
    {
        ScopeIndex stringListDefinition = new ScopeIndex("StringList", 5, 1);
        ScopeIndex handleNextStringDefn = new ScopeIndex("nextString", 5, 2);
        ScopeIndex handleEndStringDefn = new ScopeIndex("endOfStrings", 5, 3);

        ScopeIndex numberListDefinition = new ScopeIndex("NumberList", 5, 4);
        ScopeIndex handleNextNumberDefn = new ScopeIndex("nextNumber", 5, 5);
        ScopeIndex handleEndNumberDefn = new ScopeIndex("endOfNumbers", 5, 6);

        TypeDefinition stringList = new ConcreteTypeDefinition(stringListDefinition, "StringList");
        TypeDefinition handleNextString = new ConcreteTypeDefinition(handleNextStringDefn, "HandleNextString");
        TypeDefinition handleEndString = new ConcreteTypeDefinition(handleEndStringDefn, "HandleEndString");

        TypeDefinition numberList = new ConcreteTypeDefinition(numberListDefinition, "NumberList");
        TypeDefinition handleNextNumber = new ConcreteTypeDefinition(handleNextNumberDefn, "HandleNextNumber");
        TypeDefinition handleEndNumber = new ConcreteTypeDefinition(handleEndNumberDefn, "HandleEndNumber");

        Interface iStringList = new Interface(mapOf(entry("onItem", handleNextString), entry("onEnd", handleEndString)), "StringReader");
        Interface iNumberList = new Interface(mapOf(entry("onItem", handleNextNumber), entry("onEnd", handleEndNumber)), "NumberReader");

        Type stringListImpl = new StructuralType(iStringList);
        Type handleNextStringImpl = new StructuralType(new FunctionType(asList(STRING, stringList), STRING));
        Type handleEndStringImpl = new StructuralType(new FunctionType(emptyList(), STRING));

        Type numberListImpl = new StructuralType(iNumberList);
        Type handleNextNumberImpl = new StructuralType(new FunctionType(asList(NUMBER, stringList), STRING));
        Type handleEndNumberImpl = new StructuralType(new FunctionType(emptyList(), STRING));

        TypeEngine checker = new TypeEngine(TYPING_RULES, mapOf(
                entry(STRING_DEFINITION, STRING_IMPL),
                entry(NUMBER_DEFINITION, NUMBER_IMPL),
                entry(stringListDefinition, stringListImpl),
                entry(handleNextStringDefn, handleNextStringImpl),
                entry(handleEndStringDefn, handleEndStringImpl),
                entry(numberListDefinition, numberListImpl),
                entry(handleNextNumberDefn, handleNextNumberImpl),
                entry(handleEndNumberDefn, handleEndNumberImpl)
        ));

        assertNotAssignable(checker, stringList, numberList);
    }

    private void assertAssignable(TypeEngine checker, TypeDefinition source, TypeDefinition target)
    {
        assertThat(checker.canAssign(source, target), is(emptyStream()));
    }

    private void assertAssignable(TypeEngine checker, StructuralType source, StructuralType target)
    {
        assertThat(checker.canAssign(source, target), is(emptyStream()));
    }

    private void assertNotAssignable(TypeEngine checker, StructuralType source, StructuralType target)
    {
        assertThat(checker.canAssign(source, target), is(not(emptyStream())));
    }

    private void assertNotAssignable(TypeEngine checker, TypeDefinition source, TypeDefinition target)
    {
        assertThat(checker.canAssign(source, target), is(not(emptyStream())));
    }

}
