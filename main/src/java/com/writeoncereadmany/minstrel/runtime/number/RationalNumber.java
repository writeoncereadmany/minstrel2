package com.writeoncereadmany.minstrel.runtime.number;

import java.math.BigInteger;

public interface RationalNumber
{

    RationalNumber plus(RationalNumber addend);

    RationalNumber subtract(RationalNumber subtrahend);

    RationalNumber multiply(RationalNumber factor);

    RationalNumber divide(RationalNumber divisor);

    RationalNumber negate();

    RationalNumber inverse();

    BigInteger numerator();

    BigInteger denominator();
}
