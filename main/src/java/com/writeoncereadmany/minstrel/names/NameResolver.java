package com.writeoncereadmany.minstrel.names;

import com.writeoncereadmany.minstrel.ast.fragments.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameResolver
{
    private final List<String> nameResolutionErrors = new ArrayList<>();
    private final Map<Integer, Scope> scopesByIndex = new HashMap<>();
    private final Map<Terminal, ScopeIndex> types = new HashMap<>();
    private final Map<Terminal, ScopeIndex> values = new HashMap<>();
    private int nextScopeIndex = 1;
    private Scope currentScope = Scope.createRootScope(0, nameResolutionErrors::add);

    public void defineType(Terminal name)
    {
        currentScope.define(name, Kind.TYPE);
    }

    public void defineValue(Terminal name)
    {
        currentScope.define(name, Kind.VALUE);
    }

    public ScopeIndex resolveType(Terminal name)
    {
        ScopeIndex typeIndex = currentScope.resolve(name, Kind.TYPE);
        types.put(name, typeIndex);
        return typeIndex;
    }

    public ScopeIndex resolveValue(Terminal name)
    {
        ScopeIndex valueIndex = currentScope.resolve(name, Kind.VALUE);
        values.put(name, valueIndex);
        return valueIndex;
    }

    public List<String> getNameResolutionErrors()
    {
        return nameResolutionErrors;
    }

    public int enterNewScope()
    {
        final int scopeIndex = nextScopeIndex;
        nextScopeIndex++;
        currentScope = currentScope.createChildScope(scopeIndex);
        scopesByIndex.put(currentScope.getIndex(), currentScope);
        return scopeIndex;
    }

    public void enterExistingScope(int scopeToEnter)
    {
        if(currentScope.isChildScope(scopeToEnter))
        {
            currentScope = scopesByIndex.get(scopeToEnter);
        }
        else
        {
            throw new IllegalStateException("Attempting to traverse into a non-child scope");
        }
    }

    public void exitScope(int scopeToExit)
    {
        if(currentScope.getIndex() != scopeToExit)
        {
            throw new IllegalStateException("Attempting to exit scope " + scopeToExit + " but currently in scope " + currentScope.getIndex());
        }
        currentScope = currentScope.getParentScope();
    }
}
