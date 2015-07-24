package com.writeoncereadmany.minstrel.runtime;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class InefficientRatioTest
{
    @Test
    public void integersAddTogetherNicely()
    {
        InefficientRatio two = InefficientRatio.integer(2);
        InefficientRatio five = InefficientRatio.integer(5);
        InefficientRatio seven = InefficientRatio.integer(7);

        assertThat(two.plus(five), equalTo(seven));
    }

    @Test
    public void fractionsAddTogetherNicely()
    {
        InefficientRatio oneThird = InefficientRatio.ratioOf(1, 3);
        InefficientRatio oneQuarter = InefficientRatio.ratioOf(1, 4);
        InefficientRatio sevenTwelfths = InefficientRatio.ratioOf(7, 12);

        assertThat(oneThird.plus(oneQuarter), equalTo(sevenTwelfths));
    }

    @Test
    public void fractionsAreSimplified()
    {
        InefficientRatio eighteenSixths = InefficientRatio.ratioOf(18, 6);
        InefficientRatio three = InefficientRatio.integer(3);

        assertThat(eighteenSixths, equalTo(three));
    }

    @Test
    public void resultOfAdditionIsSimplified()
    {
        InefficientRatio oneThird = InefficientRatio.ratioOf(1, 3);
        InefficientRatio oneSixth = InefficientRatio.ratioOf(1, 6);
        InefficientRatio oneHalf = InefficientRatio.ratioOf(1, 2);

        assertThat(oneThird.plus(oneSixth), equalTo(oneHalf));
    }

    @Test
    public void differentWaysOfExpressingNegativeNumbersAreEquivalent()
    {
        InefficientRatio negativeNumerator = InefficientRatio.ratioOf(-2, 7);
        InefficientRatio negativeDenominator = InefficientRatio.ratioOf(2, -7);

        assertThat(negativeNumerator, equalTo(negativeDenominator));
    }
}