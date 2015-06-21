package com.writeoncereadmany.minstrel.names;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameResolver
{
    private final List<String> nameResolutionErrors = new ArrayList<>();
    private final Map<Integer, Scope> scopesByIndex = new HashMap<>();
    private int nextScopeIndex = 0;
    private Scope currentScope = Scope.createRootScope(nextScopeIndex++, nameResolutionErrors::add);

    public void defineType(String name)
    {
        currentScope.define(name, Kind.TYPE);
    }

    public void defineValue(String name)
    {
        currentScope.define(name, Kind.VALUE);
    }

    public ScopeIndex resolveType(String name)
    {
        return currentScope.resolve(name, Kind.TYPE);
    }

    public ScopeIndex resolveValue(String name)
    {
        return currentScope.resolve(name, Kind.VALUE);
    }

    public List<String> getNameResolutionErrors()
    {
        return nameResolutionErrors;
    }

    public int enterNewScope()
    {
        currentScope = currentScope.createChildScope(nextScopeIndex++);
        scopesByIndex.put(currentScope.getIndex(), currentScope);
        return currentScope.getIndex();
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
