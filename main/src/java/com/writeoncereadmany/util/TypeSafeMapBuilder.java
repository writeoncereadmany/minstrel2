package com.writeoncereadmany.util;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class TypeSafeMapBuilder
{
    @SafeVarargs
    @SuppressWarnings("uncheckedj")
    public static <K, V> Map<K, V> mapOf(Entry<K, V>... entries)
    {
        return stream(entries).collect(Collectors.toMap(entry -> entry.key, entry -> entry.value));
    }

    public static <K, V> Entry<K, V> entry(K key, V value)
    {
        return new Entry<>(key, value);
    }

    public static final class Entry<K, V>
    {
        final K key;
        final V value;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }
}
