package com.writeoncereadmany.minstrel.runtime;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class InefficientRatioTest
{
    @Test
    public void fractionsAreSimplified()
    {
        InefficientRatio eighteenSixths = InefficientRatio.ratioOf(18, 6);
        InefficientRatio three = InefficientRatio.integer(3);

        assertThat(eighteenSixths, equalTo(three));
    }

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

    @Test
    public void canNegateANumber()
    {
        InefficientRatio nineteenHalves = InefficientRatio.ratioOf(19, 2);
        InefficientRatio minusNineteenHalves = InefficientRatio.ratioOf(-19, 2);

        assertThat(nineteenHalves.negate(), equalTo(minusNineteenHalves));
    }

    @Test
    public void canSubtractNumbers()
    {
        InefficientRatio twelveSevenths = InefficientRatio.ratioOf(12, 7);
        InefficientRatio fourFifths = InefficientRatio.ratioOf(4, 5);
        InefficientRatio thirtyTwoThirtyFifths = InefficientRatio.ratioOf(32, 35);

        assertThat(twelveSevenths.subtract(fourFifths), equalTo(thirtyTwoThirtyFifths));
    }

    @Test
    public void canMultiplyIntegers()
    {
        InefficientRatio nineteen = InefficientRatio.integer(19);
        InefficientRatio seven = InefficientRatio.integer(7);
        InefficientRatio oneHundredAndThirtyThree = InefficientRatio.integer(133);

        assertThat(nineteen.multiply(seven), equalTo(oneHundredAndThirtyThree));
    }

    @Test
    public void canDivideIntegers()
    {
        InefficientRatio nineteen = InefficientRatio.integer(19);
        InefficientRatio seven = InefficientRatio.integer(7);
        InefficientRatio nineteenSevenths = InefficientRatio.ratioOf(19, 7);

        assertThat(nineteen.divide(seven), equalTo(nineteenSevenths));
    }
}