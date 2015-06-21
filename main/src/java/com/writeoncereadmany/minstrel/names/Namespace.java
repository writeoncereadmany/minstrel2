package com.writeoncereadmany.minstrel.names;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Namespace
{
    private final Map<String, Integer> names = new HashMap<>();
    private int currentIndex = 0;

    public void define(String name, Consumer<String> errorListener)
    {
        if(names.containsKey(name))
        {
            errorListener.accept("Name " + name + " already defined in this scope");
        }
        else
        {
            names.put(name, currentIndex++);
        }
    }

    public boolean contains(String name)
    {
        return names.containsKey(name);
    }

    public int indexOf(String name)
    {
        return names.getOrDefault(name, -1);
    }
}
