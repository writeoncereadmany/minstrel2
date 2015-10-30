package com.writeoncereadmany.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Collections.unmodifiableList;


public class Zipper
{
    public static <L, R> List<Pair<L, R>> zip(List<L> left, List<R> right)
    {
        if(left.size() != right.size())
        {
            throw new IllegalArgumentException("Lists are not the same size");
        }
        ArrayList<Pair<L, R>> zipped = new ArrayList<>();
        for(int i = 0; i < left.size(); i++)
        {
            zipped.add(new Pair<>(left.get(i), right.get(i)));
        }
        return unmodifiableList(zipped);
    }

    public static <L, R, Z> Stream<Z> zipShortest(Stream<L> left, Stream<R> right, BiFunction<L, R, Z> zipper)
    {
        Objects.requireNonNull(zipper);
        Iterator<L> lefterator = Spliterators.iterator(Objects.requireNonNull(left).spliterator());
        Iterator<R> righterator = Spliterators.iterator(Objects.requireNonNull(right).spliterator());
        return zip(lefterator, righterator, zipper, (a, b) -> a && b);
    }

    public static <L, R, Z> Stream<Z> zipLongest(Stream<L> left, Stream<R> right, BiFunction<L, R, Z> zipper, L defaultLeft, R defaultRight)
    {
        Objects.requireNonNull(zipper);
        Iterator<L> lefterator = new Defaulterator<>(Spliterators.iterator(Objects.requireNonNull(left).spliterator()), defaultLeft);
        Iterator<R> righterator = new Defaulterator<>(Spliterators.iterator(Objects.requireNonNull(right).spliterator()), defaultRight);

        return zip(lefterator, righterator, zipper, (a, b) -> a || b);
    }

    private static <L, R, Z> Stream<Z> zip(Iterator<L> left, Iterator<R> right, BiFunction<L, R, Z> zipper, BiFunction<Boolean, Boolean, Boolean> shouldContinue)
    {
        Iterator<Z> ziperator = new Iterator<Z>()
        {
            @Override
            public boolean hasNext()
            {
                return shouldContinue.apply(left.hasNext(), right.hasNext());
            }

            @Override
            public Z next()
            {
                return zipper.apply(left.next(), right.next());
            }
        };

        Iterable<Z> iterable = () -> ziperator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    private static class Defaulterator<T> implements Iterator<T>
    {
        private final Iterator<T> internalIterator;
        private final T defaultValue;

        private Defaulterator(Iterator<T> internalIterator, T defaultValue)
        {
            this.internalIterator = internalIterator;
            this.defaultValue = defaultValue;
        }

        @Override
        public boolean hasNext() {
            return internalIterator.hasNext();
        }

        @Override
        public T next() {
            return internalIterator.hasNext() ? internalIterator.next() : defaultValue;
        }
    }
}
