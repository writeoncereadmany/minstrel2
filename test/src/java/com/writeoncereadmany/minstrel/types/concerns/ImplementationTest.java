package com.writeoncereadmany.minstrel.types.concerns;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.Type;
import com.writeoncereadmany.minstrel.types.TypeEngine;
import com.writeoncereadmany.minstrel.types.validators.ImplementationGuaranteed;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ImplementationTest
{

    private static final TypeEngine TYPE_ENGINE = new TypeEngine(singletonList(new ImplementationGuaranteed()), null);

    public static final ScopeIndex NUMBER = new ScopeIndex(3, 2);
    public static final ScopeIndex STRING = new ScopeIndex(3, 4);
    public static final ScopeIndex TRUE = new ScopeIndex(2, 4);
    public static final ScopeIndex FALSE = new ScopeIndex(2, 5);

    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        Type anything1 = new Type();
        Type anything2 = new Type();

        assertThat(anything1.isAssignableTo(anything2, TYPE_ENGINE).collect(toList()), is(empty()));
    }


    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type sameThing = new Type(new Implementation(NUMBER));

        assertThat(aThing.isAssignableTo(sameThing, TYPE_ENGINE).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeWhichSpecifiesImplementationsIsASubtypeOfATypeWhichDoesNotSpecifyImplementation()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(bool.isAssignableTo(anything, TYPE_ENGINE).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsNotASubtypeOfATypeWhichDoes()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertThat(anything.isAssignableTo(bool, TYPE_ENGINE).collect(toList()), is(not(empty())));
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type differentThing = new Type(new Implementation(STRING));

        assertThat(aThing.isAssignableTo(differentThing, TYPE_ENGINE).collect(toList()), is(not(empty())));
    }

    @Test
    public void aTypeWhichSpecifiesASubsetOfAnothersImplementationsIsItsSubtype()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(truth.isAssignableTo(bool, TYPE_ENGINE).collect(toList()), is(empty()));
    }

    @Test
    public void aTypeIsNotASubtypeIfItHasImplementationsTheOtherDoesNot()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertThat(bool.isAssignableTo(truth, TYPE_ENGINE).collect(toList()), is(not(empty())));
    }
}
