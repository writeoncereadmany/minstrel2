package com.writeoncereadmany.minstrel.names;

import java.util.ArrayList;
import java.util.List;

public class NameResolver
{
    // this is the wrong approach, but until I introduce scoped constructs, it's good enough :)

    private final List<String> nameResolutionErrors = new ArrayList<>();
    private final Namespace types = new Namespace(nameResolutionErrors::add);
    private final Namespace values = new Namespace(nameResolutionErrors::add);

    public void defineType(String name)
    {
        types.define(name);
    }

    public ScopeIndex resolveType(String name)
    {
        return types.resolve(name);
    }

    public void defineValue(String name)
    {
        values.define(name);
    }

    public ScopeIndex resolveValue(String name)
    {
        return values.resolve(name);
    }

    public List<String> getNameResolutionErrors()
    {
        return nameResolutionErrors;
    }
}
