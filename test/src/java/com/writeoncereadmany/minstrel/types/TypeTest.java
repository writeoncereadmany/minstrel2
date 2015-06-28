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
        DiscreteType type1 = new DiscreteType();
        DiscreteType type2 = new DiscreteType();

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void aTypeWhichDoesNotSpecifyImplementationIsASubtypeOfOneWhichDoes()
    {
        DiscreteType type1 = new DiscreteType();
        DiscreteType type2 = new DiscreteType(new Implementation(new ScopeIndex(3, 2)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void anImplementationIsASubtypeOfItself()
    {
        DiscreteType type1 = new DiscreteType(new Implementation(new ScopeIndex(3, 2)));
        DiscreteType type2 = new DiscreteType(new Implementation(new ScopeIndex(3, 2)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(empty()));
    }

    @Test
    public void anImplementationIsNotASubtypeOfAnotherImplementation()
    {
        DiscreteType type1 = new DiscreteType(new Implementation(new ScopeIndex(3, 2)));
        DiscreteType type2 = new DiscreteType(new Implementation(new ScopeIndex(3, 4)));

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(not(empty())));
    }

    @Test
    public void aTypeWhichSpecifiesImplementationIsNotASubtypeOfOneWhichDoesNot()
    {
        DiscreteType type1 = new DiscreteType(new Implementation(new ScopeIndex(3, 2)));
        DiscreteType type2 = new DiscreteType();

        assertThat(type1.isSubtypeOf(type2, asList(new ImplementationGuaranteed())), is(not(empty())));
    }
}