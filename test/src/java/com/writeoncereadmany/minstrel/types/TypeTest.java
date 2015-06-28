package com.writeoncereadmany.minstrel.types;

import com.writeoncereadmany.minstrel.names.ScopeIndex;
import com.writeoncereadmany.minstrel.types.concerns.Implementation;
import com.writeoncereadmany.minstrel.types.validators.ImplementationGuaranteed;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class TypeTest
{
    @Test
    public void aTypeWhichDoNotSpecifyImplementationIsASubtypeOfAnotherWhichDoesNotSpecifyImplementation()
    {
        Type type1 = new Type();
        Type type2 = new Type();

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsASubtypeOfOneWhichDoes()
    {
        Type type1 = new Type();
        Type type2 = new Type(new Implementation(new ScopeIndex(3, 2)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        Type type1 = new Type(new Implementation(new ScopeIndex(3, 2)));
        Type type2 = new Type(new Implementation(new ScopeIndex(3, 2)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        Type type1 = new Type(new Implementation(new ScopeIndex(3, 2)));
        Type type2 = new Type(new Implementation(new ScopeIndex(3, 4)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(not(empty())));
    }

    @Test
    public void aTypeWhichSpecifiesImplementationIsNotASubtypeOfOneWhichDoesNot()
    {
        Type type1 = new Type(new Implementation(new ScopeIndex(3, 2)));
        Type type2 = new Type();

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(not(empty())));
    }
}