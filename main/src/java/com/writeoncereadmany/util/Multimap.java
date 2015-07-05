package com.writeoncereadmany.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Multimap<K, V>
{
    private final Map<K, Set<V>> entries = new HashMap<>();

    public void put(K key, V value)
    {
        if(!entries.containsKey(key))
        {
            entries.put(key, new HashSet<>());
        }
        entries.get(key).add(value);
    }

    public boolean contains(K key, V value)
    {
        Set<V> values = entries.get(key);
        if(values == null)
        {
            return false;
        }
        return values.contains(value);
    }

    public void clear()
    {
        entries.clear();
    }
}
