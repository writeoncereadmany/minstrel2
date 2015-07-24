package com.writeoncereadmany.minstrel.runtime;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Ratio class which implements the functional requirements, albeit not in a particularly efficient way
 */
public final class InefficientRatio
{
    private final BigInteger numerator;
    private final BigInteger denominator;

    private InefficientRatio(BigInteger numerator, BigInteger denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static InefficientRatio integer(long value)
    {
        return integer(BigInteger.valueOf(value));
    }

    public static InefficientRatio integer(BigInteger value)
    {
        return new InefficientRatio(value, BigInteger.ONE);
    }

    public static InefficientRatio ratioOf(long numerator, long divisor)
    {
        return ratioOf(BigInteger.valueOf(numerator), BigInteger.valueOf(divisor));
    }

    public static InefficientRatio ratioOf(BigInteger numerator, BigInteger denominator)
    {
        BigInteger gcd = numerator.gcd(denominator);
        BigInteger simplifiedNumerator = numerator.divide(gcd);
        BigInteger simplifiedDenominator = denominator.divide(gcd);

        int compareDenominatorToZero = denominator.compareTo(BigInteger.ZERO);

        if(compareDenominatorToZero > 0)
        {
            return new InefficientRatio(simplifiedNumerator, simplifiedDenominator);
        }
        else if(compareDenominatorToZero < 0)
        {
            return new InefficientRatio(simplifiedNumerator.negate(), simplifiedDenominator.negate());
        }
        else
        {
            throw new IllegalArgumentException("Cannot make a number with denominator 0");
        }
    }

    public InefficientRatio plus(InefficientRatio addend)
    {
        BigInteger commonDenominator = denominator.multiply(addend.denominator);
        BigInteger myNewNumerator = numerator.multiply(addend.denominator);
        BigInteger hisNewNumerator = addend.numerator.multiply(denominator);

        return ratioOf(myNewNumerator.add(hisNewNumerator), commonDenominator);
    }

    public InefficientRatio subtract(InefficientRatio subtrahend)
    {
        return this;
    }

    public InefficientRatio multiply(InefficientRatio factor)
    {
        return this;
    }

    public InefficientRatio divide(InefficientRatio divisor)
    {
        return this;
    }

    public InefficientRatio negate()
    {
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof InefficientRatio))
        {
            return false;
        }
        InefficientRatio that = (InefficientRatio)o;
        return this.numerator.equals(that.numerator) && this.denominator.equals(that.denominator);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString()
    {
        return BigInteger.ONE.equals(denominator)
                ? numerator.toString()
                : numerator.toString() + " / " + denominator.toString();
    }
}
