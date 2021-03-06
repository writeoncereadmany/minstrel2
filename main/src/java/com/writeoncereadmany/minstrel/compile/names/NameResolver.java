package com.writeoncereadmany.minstrel.compile.names;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameResolver
{
    private final List<String> nameResolutionErrors = new ArrayList<>();
    private final Map<Integer, Scope> scopesByIndex = new HashMap<>();
    private int nextScopeIndex = 1;
    private Scope currentScope = Scope.createRootScope(0, nameResolutionErrors::add);

    public void define(Terminal name, Kind kind)
    {
        currentScope.define(name, kind);
        name.setScopeIndex(currentScope.resolve(name, kind));
    }

    public void resolve(Terminal name, Kind kind)
    {
        name.setScopeIndex(currentScope.resolve(name, kind));
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
