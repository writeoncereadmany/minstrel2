package com.writeoncereadmany.minstrel.runtime.number;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Ratio class which implements the functional requirements, albeit not in a particularly efficient way.
 * Guarantees that all instances are expressed in their simplest possible form.
 */
public final class InefficientRatio implements RationalNumber
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

    public static InefficientRatio parse(String numericLiteral)
    {
        try
        {
            String[] parts = numericLiteral.split("\\.");
            if (parts.length == 1)
            {
                return integer(new BigInteger(parts[0]));
            }
            if (parts.length != 2)
            {
                throw new NumberFormatException();
            }
            BigInteger integerPart = new BigInteger(parts[0]);
            BigInteger decimalPart = new BigInteger(parts[1]);
            int decimalPlaces = parts[1].length();
            BigInteger denominator = BigInteger.TEN.pow(decimalPlaces);
            return ratioOf(integerPart.multiply(denominator).add(decimalPart), denominator);
        }
        catch(NumberFormatException ex)
        {
            throw new IllegalArgumentException("Cannot parse illegal number literal: " + numericLiteral);
        }
    }

    @Override
    public RationalNumber plus(RationalNumber addend)
    {
        BigInteger commonDenominator = denominator.multiply(addend.denominator());
        BigInteger myNewNumerator = numerator.multiply(addend.denominator());
        BigInteger hisNewNumerator = addend.numerator().multiply(denominator);

        return new InefficientRatio(myNewNumerator.add(hisNewNumerator), commonDenominator);
    }

    @Override
    public RationalNumber subtract(RationalNumber subtrahend)
    {
        return plus(subtrahend.negate());
    }

    @Override
    public RationalNumber multiply(RationalNumber factor)
    {
        return new InefficientRatio(numerator.multiply(factor.numerator()),
                                    denominator.multiply(factor.denominator()));
    }

    @Override
    public RationalNumber divide(RationalNumber divisor)
    {
        return multiply(divisor.inverse());
    }

    @Override
    public RationalNumber negate()
    {
        return new InefficientRatio(numerator.negate(), denominator);
    }

    @Override
    public RationalNumber inverse()
    {
        return new InefficientRatio(denominator, numerator);
    }

    @Override
    public BigInteger numerator()
    {
        return numerator;
    }

    @Override
    public BigInteger denominator()
    {
        return denominator;
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
