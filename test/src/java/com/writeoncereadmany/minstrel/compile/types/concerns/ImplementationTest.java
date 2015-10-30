package com.writeoncereadmany.minstrel.compile.types.concerns;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import org.junit.Test;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ImplementationTest
{
    public static final ScopeIndex NUMBER = new ScopeIndex("Number", 3, 2);
    public static final ScopeIndex STRING = new ScopeIndex("String", 3, 4);
    public static final ScopeIndex TRUE = new ScopeIndex("True", 2, 4);
    public static final ScopeIndex FALSE = new ScopeIndex("False", 2, 5);

    private final TypeEngine typeEngine = new TypeEngine(singletonList(new ImplementationRule()), emptyMap());

    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        StructuralType anything1 = new StructuralType();
        StructuralType anything2 = new StructuralType();

        assertAssignable(anything1, anything2);
    }

    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        StructuralType aThing = new StructuralType(new Implementation(NUMBER));
        StructuralType sameThing = new StructuralType(new Implementation(NUMBER));

        assertAssignable(aThing, sameThing);
    }

    @Test
    public void aTypeWhichSpecifiesImplementationsIsASubtypeOfATypeWhichDoesNotSpecifyImplementation()
    {
        StructuralType bool = new StructuralType(new Implementation(TRUE, FALSE));
        StructuralType anything = new StructuralType();

        assertAssignable(bool, anything);
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsNotASubtypeOfATypeWhichDoes()
    {
        StructuralType bool = new StructuralType(new Implementation(TRUE, FALSE));
        StructuralType anything = new StructuralType();

        assertNotAssignable(anything, bool);
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        StructuralType aThing = new StructuralType(new Implementation(NUMBER));
        StructuralType differentThing = new StructuralType(new Implementation(STRING));

        assertNotAssignable(aThing, differentThing);
    }

    @Test
    public void aTypeWhichSpecifiesASubsetOfAnothersImplementationsIsItsSubtype()
    {
        StructuralType bool = new StructuralType(new Implementation(TRUE, FALSE));
        StructuralType truth = new StructuralType(new Implementation(TRUE));

        assertAssignable(truth, bool);
    }

    @Test
    public void aTypeIsNotASubtypeIfItHasImplementationsTheOtherDoesNot()
    {
        StructuralType bool = new StructuralType(new Implementation(TRUE, FALSE));
        StructuralType truth = new StructuralType(new Implementation(TRUE));

        assertNotAssignable(bool, truth);
    }

    private void assertNotAssignable(StructuralType source, StructuralType target)
    {
        assertThat(typeEngine.canAssign(source, target), is(not(emptyStream())));
    }

    private void assertAssignable(StructuralType source, StructuralType target)
    {
        assertThat(typeEngine.canAssign(source, target), is(emptyStream()));
    }

}
