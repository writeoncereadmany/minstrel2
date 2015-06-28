package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.validators.ImplementationGuaranteed;
import com.writeoncereadmany.minstrel.types.validators.TypingRule;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ImplementationTest
{
    public static final List<TypingRule> TYPING_RULES = singletonList(new ImplementationGuaranteed());

    public static final ScopeIndex NUMBER = new ScopeIndex(3, 2);
    public static final ScopeIndex STRING = new ScopeIndex(3, 4);
    public static final ScopeIndex TRUE = new ScopeIndex(2, 4);
    public static final ScopeIndex FALSE = new ScopeIndex(2, 5);

    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        Type anything1 = new Type();
        Type anything2 = new Type();

        assertThat(anything1.isSubtypeOf(anything2, TYPING_RULES), is(empty()));
    }


    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type sameThing = new Type(new Implementation(NUMBER));

        assertThat(aThing.isSubtypeOf(sameThing, TYPING_RULES), is(empty()));
    }

    @Test
    public void aTypeWhichSpecifiesImplementationsIsASubtypeOfATypeWhichDoesNotSpecifyImplementation()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(bool.isSubtypeOf(anything, TYPING_RULES), is(empty()));
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsNotASubtypeOfATypeWhichDoes()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(anything.isSubtypeOf(bool, TYPING_RULES), is(not(empty())));
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type differentThing = new Type(new Implementation(STRING));

        assertThat(aThing.isSubtypeOf(differentThing, TYPING_RULES), is(not(empty())));
    }

    @Test
    public void aTypeWhichSpecifiesASubsetOfAnothersImplementationsIsItsSubtype()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(truth.isSubtypeOf(bool, TYPING_RULES), is(empty()));
    }

    @Test
    public void aTypeIsNotASubtypeIfItHasImplementationsTheOtherDoesNot()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(bool.isSubtypeOf(truth, TYPING_RULES), is(not(empty())));
    }
}
