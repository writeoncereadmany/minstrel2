package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeChecker;
import com.writeoncereadmany.minstrel.types.validators.ImplementationGuaranteed;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ImplementationTest
{

    private static final TypeChecker TYPE_CHECKER = new TypeChecker(singletonList(new ImplementationGuaranteed()), null);

    public static final ScopeIndex NUMBER = new ScopeIndex(3, 2);
    public static final ScopeIndex STRING = new ScopeIndex(3, 4);
    public static final ScopeIndex TRUE = new ScopeIndex(2, 4);
    public static final ScopeIndex FALSE = new ScopeIndex(2, 5);

    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        Type anything1 = new Type();
        Type anything2 = new Type();

        assertThat(TYPE_CHECKER.canAssign(anything1, anything2).collect(toList()), is(empty()));
    }


    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type sameThing = new Type(new Implementation(NUMBER));

        assertThat(TYPE_CHECKER.canAssign(aThing, sameThing).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeWhichSpecifiesImplementationsIsASubtypeOfATypeWhichDoesNotSpecifyImplementation()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(TYPE_CHECKER.canAssign(bool, anything).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsNotASubtypeOfATypeWhichDoes()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(TYPE_CHECKER.canAssign(anything, bool).collect(toList()), is(not(empty())));
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type differentThing = new Type(new Implementation(STRING));

        assertThat(TYPE_CHECKER.canAssign(aThing, differentThing).collect(toList()), is(not(empty())));
    }

    @Test
    public void aTypeWhichSpecifiesASubsetOfAnothersImplementationsIsItsSubtype()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(TYPE_CHECKER.canAssign(truth, bool).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeIsNotASubtypeIfItHasImplementationsTheOtherDoesNot()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(TYPE_CHECKER.canAssign(bool, truth).collect(toList()), is(not(empty())));
    }
}
