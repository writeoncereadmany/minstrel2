package com.writeoncereadmany.minstrel.names;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.Arrays.stream;

public class Scope
{
    private final Scope parentScope;
    private final int index;
    private final Consumer<String> errorListener;
    private final Set<Integer> immediateChildren = new HashSet<>();
    private final EnumMap<Kind, Namespace> namespaces;

    private Scope(int index, Scope parentScope, Consumer<String> errorListener)
    {
        this.index = index;
        this.parentScope = parentScope;
        this.errorListener = errorListener;
        namespaces = new EnumMap<>(Kind.class);
        stream(Kind.values()).forEach(kind -> namespaces.put(kind, new Namespace()));
    }

    public void define(String name, Kind kind)
    {
        namespaces.get(kind).define(name, errorListener);
    }

    public ScopeIndex resolve(String name, Kind kind)
    {
        Namespace namespace = namespaces.get(kind);
        if(namespace.contains(name))
        {
            return new ScopeIndex(index, namespace.indexOf(name));
        }
        else if(parentScope != null)
        {
            return parentScope.resolve(name, kind);
        }
        else
        {
            errorListener.accept("Could not find a definition for " + name);
            return ScopeIndex.UNDEFINED;
        }
    }

    public Scope createChildScope(int index)
    {
        immediateChildren.add(index);
        return new Scope(index, this, errorListener);
    }

    public Scope getParentScope()
    {
        return parentScope;
    }

    public static Scope createRootScope(int index, Consumer<String> errorListener)
    {
        return new Scope(index, null, errorListener);
    }

    public int getIndex()
    {
        return index;
    }

    public boolean isChildScope(int index)
    {
        return immediateChildren.contains(index);
    }
}
