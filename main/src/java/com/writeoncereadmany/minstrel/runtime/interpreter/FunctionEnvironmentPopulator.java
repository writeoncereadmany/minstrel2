package com.writeoncereadmany.minstrel.runtime.interpreter;

import com.writeoncereadmany.minstrel.compile.ast.fragments.Parameter;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;
import com.writeoncereadmany.minstrel.compile.names.Kind;
import com.writeoncereadmany.minstrel.compile.names.NameResolver;
import com.writeoncereadmany.minstrel.compile.visitors.UnsupportedVisitor;
import com.writeoncereadmany.minstrel.runtime.environment.Environment;
import com.writeoncereadmany.minstrel.runtime.values.Value;

import java.util.ArrayDeque;
import java.util.List;

public class FunctionEnvironmentPopulator extends UnsupportedVisitor
{
    private final NameResolver nameResolver;
    private final Environment environment;
    private final ArrayDeque<Value> arguments;

    public FunctionEnvironmentPopulator(NameResolver nameResolver, Environment environment, List<Value> arguments)
    {
        this.environment = environment;
        this.nameResolver = nameResolver;
        this.arguments = new ArrayDeque<>(arguments);
    }

    @Override
    public void visitParameter(Terminal type, Terminal name)
    {
        Value argument = arguments.poll();
        if(argument == null)
        {
            throw new IllegalArgumentException("Insufficient arguments provided for function call");
        }
        environment.declare(nameResolver.lookup(name, Kind.VALUE), argument);
    }

    @Override
    public void visitParameterList(List<Parameter> parameters)
    {
        parameters.forEach(parameter -> parameter.visit(this));
        if(!arguments.isEmpty())
        {
            throw new IllegalArgumentException("Too many arguments provided for function call");
        }
    }
}
