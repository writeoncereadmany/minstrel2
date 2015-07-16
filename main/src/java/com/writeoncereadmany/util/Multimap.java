package com.writeoncereadmany.util;

import java.util.*;

public class Multimap<K, V>
{
    private final Map<K, Set<V>> entries = new HashMap<>();

    public void put(K key, V value)
    {
        entries.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    public boolean contains(K key, V value)
    {
        return entries.getOrDefault(key, Collections.emptySet()).contains(value);
    }

    public void clear()
    {
        entries.clear();
    }
}
