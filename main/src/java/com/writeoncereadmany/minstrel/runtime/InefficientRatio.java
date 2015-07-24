package com.writeoncereadmany.minstrel.runtime;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Ratio class which implements the functional requirements, albeit not in a particularly efficient way.
 * Guarantees that all instances are expressed in their simplest possible form.
 */
public final class InefficientRatio
{
    private final BigInteger numerator;
    private final BigInteger denominator;

    private InefficientRatio(BigInteger numerator, BigInteger denominator)
    {
        BigInteger gcd = numerator.gcd(denominator);
        BigInteger simplifiedNumerator = numerator.divide(gcd);
        BigInteger simplifiedDenominator = denominator.divide(gcd);

        int compareDenominatorToZero = denominator.compareTo(BigInteger.ZERO);

        if(compareDenominatorToZero > 0)
        {
            this.numerator = simplifiedNumerator;
            this.denominator = simplifiedDenominator;
        }
        else if(compareDenominatorToZero < 0)
        {
            this.numerator = simplifiedNumerator.negate();
            this.denominator = simplifiedDenominator.negate();
        }
        else
        {
            throw new IllegalArgumentException("Cannot make a number with denominator 0");
        }
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
        return new InefficientRatio(numerator, denominator);
    }

    public InefficientRatio plus(InefficientRatio addend)
    {
        BigInteger commonDenominator = denominator.multiply(addend.denominator);
        BigInteger myNewNumerator = numerator.multiply(addend.denominator);
        BigInteger hisNewNumerator = addend.numerator.multiply(denominator);

        return new InefficientRatio(myNewNumerator.add(hisNewNumerator), commonDenominator);
    }

    public InefficientRatio subtract(InefficientRatio subtrahend)
    {
        return plus(subtrahend.negate());
    }

    public InefficientRatio multiply(InefficientRatio factor)
    {
        return new InefficientRatio(numerator.multiply(factor.numerator),
                                    denominator.multiply(factor.denominator));
    }

    public InefficientRatio divide(InefficientRatio divisor)
    {
        return multiply(divisor.inverse());
    }

    public InefficientRatio negate()
    {
        return new InefficientRatio(numerator.negate(), denominator);
    }

    public InefficientRatio inverse()
    {
        return new InefficientRatio(denominator, numerator);
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
