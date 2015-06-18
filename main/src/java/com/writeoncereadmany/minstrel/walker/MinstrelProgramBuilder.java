package com.writeoncereadmany.minstrel.walker;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Program;
import com.writeoncereadmany.minstrel.builders.AstNodeBuilder;

import java.util.Stack;

public class MinstrelProgramBuilder
{
    private final Stack<AstNodeBuilder<?>> beingBuilt = new Stack<>();
    private Program program;

    public Program getResult()
    {
        return this.program;
    }

    void startBuildingNode(AstNodeBuilder<?> builder)
    {
        beingBuilt.push(builder);
    }

    AstNode finishBuildingNode()
    {
        return beingBuilt.pop().build();
    }

    void addNodeToCurrent(AstNode node)
    {
        beingBuilt.peek().addNode(node);
    }

    void addTerminalToCurrent(String text)
    {
        beingBuilt.peek().addTerminal(text);
    }

    void finish(Program program)
    {
        this.program = program;
    }

}
