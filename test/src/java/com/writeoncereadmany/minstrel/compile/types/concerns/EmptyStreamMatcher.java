package com.writeoncereadmany.minstrel.compile.types.concerns;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.stream.Stream;

public class EmptyStreamMatcher extends TypeSafeMatcher<Stream<?>>
{
    @Override
    protected boolean matchesSafely(Stream<?> stream)
    {
        return stream.findFirst().map(x -> false).orElse(true);
    }

    @Override
    public void describeTo(Description description)
    {

    }

    public static Matcher<Stream<?>> emptyStream()
    {
        return new EmptyStreamMatcher();
    }
}
