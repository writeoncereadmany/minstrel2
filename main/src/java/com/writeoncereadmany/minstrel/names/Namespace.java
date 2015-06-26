package com.writeoncereadmany.minstrel.names;


import com.writeoncereadmany.minstrel.ast.fragments.Terminal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Namespace
{
    private final Map<String, Integer> names = new HashMap<>();
    private int currentIndex = 0;

    public void define(Terminal name, Consumer<String> errorListener)
    {
        if(names.containsKey(name.text))
        {
            errorListener.accept("Name error on line " + name.line + ", column " + name.column +
                                 ": " + name.text + " already defined in this scope");
        }
        else
        {
            names.put(name.text, currentIndex++);
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
