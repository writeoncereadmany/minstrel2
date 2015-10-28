package com.writeoncereadmany.minstrel.compile.listener;

import com.writeoncereadmany.minstrel.compile.Source;
import com.writeoncereadmany.minstrel.compile.ast.AstNode;
import com.writeoncereadmany.minstrel.compile.ast.fragments.Terminal;

@FunctionalInterface
interface TerminalConstructor
{
    AstNode construct(Source source, Terminal terminal);
}
