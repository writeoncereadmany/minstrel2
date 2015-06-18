package com.writeoncereadmany.minstrel.listener;

import com.writeoncereadmany.minstrel.ast.AstNode;
import com.writeoncereadmany.minstrel.ast.Program;
import com.writeoncereadmany.minstrel.astbuilders.AstNodeBuilder;

import java.util.Stack;

public class ASTBuilder
{
    private final Stack<AstNodeBuilder<?>> beingBuilt = new Stack<>();
    private Program program;

    public Program getProgram()
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
