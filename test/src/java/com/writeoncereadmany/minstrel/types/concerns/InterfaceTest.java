package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
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

    private static final Type NUMBER = new Type(new Implementation(NUMBER_DEFINITION));
    private static final Type STRING = new Type(new Implementation(STRING_DEFINITION));
    private static final Type ANIMAL = new Type(new Implementation(CAT_DEFINITION, DOG_DEFINITION, FISH_DEFINITION));
    private static final Type MAMMAL = new Type(new Implementation(CAT_DEFINITION, DOG_DEFINITION));
    private static final Type CAT = new Type(new Implementation(CAT_DEFINITION));

    private static final Map<ScopeIndex, Type> TYPES = mapOf(
            entry(NUMBER_DEFINITION, NUMBER),
            entry(STRING_DEFINITION, STRING),
            entry(CAT_DEFINITION, CAT),
            entry(MAMMAL_DEFINITION, MAMMAL),
            entry(ANIMAL_DEFINITION, ANIMAL)
    );

    private static final List<TypingRule> TYPING_RULES = asList(new ImplementationRule(), new FunctionRules(), new InterfaceRule());

    private TypeChecker checker = new TypeChecker(TYPING_RULES, TYPES);


    @Test
    public void anInterfaceIsAssignableToItself()
    {
        Type point2d = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));
        Type vector2d = new Type(new Interface(mapOf(entry("x", NUMBER), entry("y", NUMBER))));

        assertThat(checker.canAssign(point2d, vector2d), is(emptyStream()));
    }


}
