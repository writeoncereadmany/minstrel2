package com.writeoncereadmany.minstrel.runtime;

import com.writeoncereadmany.minstrel.runtime.number.InefficientRatio;
import org.junit.Test;

import java.math.BigInteger;

import static com.writeoncereadmany.minstrel.runtime.number.InefficientRatio.integer;
import static com.writeoncereadmany.minstrel.runtime.number.InefficientRatio.parse;
import static com.writeoncereadmany.minstrel.runtime.number.InefficientRatio.ratioOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class InefficientRatioTest
{
    @Test
    public void fractionsAreSimplified()
    {
        InefficientRatio eighteenSixths = ratioOf(18, 6);
        InefficientRatio three = integer(3);

        assertThat(eighteenSixths, equalTo(three));
    }

    @Test
    public void integersAddTogetherNicely()
    {
        InefficientRatio two = integer(2);
        InefficientRatio five = integer(5);
        InefficientRatio seven = integer(7);

        assertThat(two.plus(five), equalTo(seven));
    }

    @Test
    public void fractionsAddTogetherNicely()
    {
        InefficientRatio oneThird = ratioOf(1, 3);
        InefficientRatio oneQuarter = ratioOf(1, 4);
        InefficientRatio sevenTwelfths = ratioOf(7, 12);

        assertThat(oneThird.plus(oneQuarter), equalTo(sevenTwelfths));
    }

    @Test
    public void resultOfAdditionIsSimplified()
    {
        InefficientRatio oneThird = ratioOf(1, 3);
        InefficientRatio oneSixth = ratioOf(1, 6);
        InefficientRatio oneHalf = ratioOf(1, 2);

        assertThat(oneThird.plus(oneSixth), equalTo(oneHalf));
    }

    @Test
    public void differentWaysOfExpressingNegativeNumbersAreEquivalent()
    {
        InefficientRatio negativeNumerator = ratioOf(-2, 7);
        InefficientRatio negativeDenominator = ratioOf(2, -7);

        assertThat(negativeNumerator, equalTo(negativeDenominator));
    }

    @Test
    public void canNegateANumber()
    {
        InefficientRatio nineteenHalves = ratioOf(19, 2);
        InefficientRatio minusNineteenHalves = ratioOf(-19, 2);

        assertThat(nineteenHalves.negate(), equalTo(minusNineteenHalves));
    }

    @Test
    public void canSubtractNumbers()
    {
        InefficientRatio twelveSevenths = ratioOf(12, 7);
        InefficientRatio fourFifths = ratioOf(4, 5);
        InefficientRatio thirtyTwoThirtyFifths = ratioOf(32, 35);

        assertThat(twelveSevenths.subtract(fourFifths), equalTo(thirtyTwoThirtyFifths));
    }

    @Test
    public void canMultiplyIntegers()
    {
        InefficientRatio nineteen = integer(19);
        InefficientRatio seven = integer(7);
        InefficientRatio oneHundredAndThirtyThree = integer(133);

        assertThat(nineteen.multiply(seven), equalTo(oneHundredAndThirtyThree));
    }

    @Test
    public void canDivideIntegers()
    {
        InefficientRatio nineteen = integer(19);
        InefficientRatio seven = integer(7);
        InefficientRatio nineteenSevenths = ratioOf(19, 7);

        assertThat(nineteen.divide(seven), equalTo(nineteenSevenths));
    }

    @Test
    public void canParseIntegers()
    {
        assertThat(parse("1353"), equalTo(integer(1353)));
    }

    @Test
    public void canParseLargeNegativeIntegers()
    {
        assertThat(parse("-934850923908340923453"), equalTo(integer(new BigInteger("-934850923908340923453"))));
    }

    @Test
    public void canParseDecimals()
    {
        assertThat(parse("123.456"), equalTo(ratioOf(123456, 1000)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonNumbers()
    {
        parse("five");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTooManyDecimalPoints()
    {
        parse("1.2.3");
    }
}