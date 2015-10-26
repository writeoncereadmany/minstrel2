package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeChecker;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import org.junit.Test;

import java.util.Collections;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ImplementationTest
{
    public static final ScopeIndex NUMBER = new ScopeIndex(3, 2);
    public static final ScopeIndex STRING = new ScopeIndex(3, 4);
    public static final ScopeIndex TRUE = new ScopeIndex(2, 4);
    public static final ScopeIndex FALSE = new ScopeIndex(2, 5);

    private final TypeChecker typeChecker = new TypeChecker(singletonList(new ImplementationRule()), emptyMap(), emptyMap());

    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        Type anything1 = new Type();
        Type anything2 = new Type();

        assertAssignable(anything1, anything2);
    }

    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type sameThing = new Type(new Implementation(NUMBER));

        assertAssignable(aThing, sameThing);
    }

    @Test
    public void aTypeWhichSpecifiesImplementationsIsASubtypeOfATypeWhichDoesNotSpecifyImplementation()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertAssignable(bool, anything);
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsNotASubtypeOfATypeWhichDoes()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type anything = new Type();

        assertNotAssignable(anything, bool);
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        Type aThing = new Type(new Implementation(NUMBER));
        Type differentThing = new Type(new Implementation(STRING));

        assertNotAssignable(aThing, differentThing);
    }

    @Test
    public void aTypeWhichSpecifiesASubsetOfAnothersImplementationsIsItsSubtype()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertAssignable(truth, bool);
    }

    @Test
    public void aTypeIsNotASubtypeIfItHasImplementationsTheOtherDoesNot()
    {
        Type bool = new Type(new Implementation(TRUE, FALSE));
        Type truth = new Type(new Implementation(TRUE));

        assertNotAssignable(bool, truth);
    }

    private void assertNotAssignable(Type source, Type target)
    {
        assertThat(typeChecker.canAssign(source, target), is(not(emptyStream())));
    }

    private void assertAssignable(Type source, Type target)
    {
        assertThat(typeChecker.canAssign(source, target), is(emptyStream()));
    }

}
