package com.writeoncereadmany.minstrel.runtime.environment;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private final Map<ScopeIndex, Value> values;

    public Environment()
    {
        this(new HashMap<>());
    }

    public Environment(HashMap<ScopeIndex, Value> values)
    {
        this.values = values;
    }

    public void declare(ScopeIndex index, Value value)
    {
        values.put(index, value);
    }

    public Value get(ScopeIndex index)
    {
        return values.get(index);
    }

    public Environment createChild()
    {
        return new Environment(new HashMap<>(values));
    }
}
