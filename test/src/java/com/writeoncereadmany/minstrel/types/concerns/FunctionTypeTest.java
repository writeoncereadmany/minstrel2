package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.validators.FunctionTypingRules;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FunctionTypeTest
{
    private final ScopeIndex CAT_DEF = new ScopeIndex(3, 2);
    private final ScopeIndex DOG_DEF = new ScopeIndex(3, 2);
    private final ScopeIndex FISH_DEF = new ScopeIndex(3, 2);

    private final Type ANIMAL = new Type(new Implementation(CAT_DEF, DOG_DEF, FISH_DEF));
    private final Type MAMMAL = new Type(new Implementation(CAT_DEF, DOG_DEF));
    private final Type CAT = new Type(new Implementation(CAT_DEF));

    private final List<TypingRule> FUNCTION_TYPING_RULES = singletonList(new FunctionTypingRules());

    @Test
    public void aFunctionTypeIsASubtypeOfItself()
    {
        Type aFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));
        Type sameFunction = new Type(new FunctionType(singletonList(MAMMAL), MAMMAL));

        assertThat(aFunction.isAssignableTo(sameFunction, FUNCTION_TYPING_RULES), is(empty()));
    }
}
