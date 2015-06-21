package com.writeoncereadmany.minstrel.names;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Namespace
{
    private final Map<String, ScopeIndex> types = new HashMap<>();
    private final Consumer<String> onNameResolutionError;
    private int currentTypeIndex = 0;

    public Namespace(Consumer<String> onNameResolutionError)
    {
        this.onNameResolutionError = onNameResolutionError;
    }

    public void define(String name)
    {
        if(types.containsKey(name))
        {
            onNameResolutionError.accept("Type " + name + " already defined");
        }
        types.put(name, new ScopeIndex(0, currentTypeIndex++));
    }

    public ScopeIndex resolve(String name)
    {
        if(types.containsKey(name))
        {
            return types.get(name);
        }
        else
        {
            onNameResolutionError.accept("Cannot resolve type " + name);
            return ScopeIndex.UNDEFINED;
        }
    }
}
