package com.writeoncereadmany.minstrel.names;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameResolver
{
    // this is the wrong approach, but until I introduce scoped constructs, it's good enough :)
    private final Map<String, ScopeIndex> types = new HashMap<>();
    private final Map<String, ScopeIndex> values = new HashMap<>();

    private int currentTypeIndex = 0;
    private int currentValueIndex = 0;

    private final List<String> nameResolutionErrors = new ArrayList<>();

    public void defineType(String name)
    {
        if(types.containsKey(name))
        {
            nameResolutionErrors.add("Type " + name + " already defined");
        }
        types.put(name, new ScopeIndex(0, currentTypeIndex++));
    }

    public ScopeIndex resolveType(String name)
    {
        if(types.containsKey(name))
        {
            return types.get(name);
        }
        else
        {
            nameResolutionErrors.add("Cannot resolve type " + name);
            return ScopeIndex.UNDEFINED;
        }
    }
    public void defineValue(String name)
    {
        if(values.containsKey(name))
        {
            nameResolutionErrors.add("Value " + name + " already defined");
        }
        values.put(name, new ScopeIndex(0, currentValueIndex++));
    }

    public ScopeIndex resolveValue(String name)
    {
        if(values.containsKey(name))
        {
            return values.get(name);
        }
        else
        {
            nameResolutionErrors.add("Cannot resolve value " + name);
            return ScopeIndex.UNDEFINED;
        }
    }

    public List<String> getNameResolutionErrors()
    {
        return nameResolutionErrors;
    }
}
