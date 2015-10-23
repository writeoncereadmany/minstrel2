package com.writeoncereadmany.minstrel.orchestrator;

import com.writeoncereadmany.minstrel.compile.ast.Program;
import com.writeoncereadmany.minstrel.compile.listener.ASTBuilder;
import com.writeoncereadmany.minstrel.compile.listener.ASTBuildingListener;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelLexer;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

public class MinstrelOrchestrator
{
    private final ANTLRErrorListener lexErrorListener;
    private final ANTLRErrorListener parseErrorListener;

    public MinstrelOrchestrator(ANTLRErrorListener lexErrorListener, ANTLRErrorListener parseErrorListener)
    {
        this.lexErrorListener = lexErrorListener;
        this.parseErrorListener = parseErrorListener;
    }

    public TokenStream lex(InputStream inputStream) throws IOException
    {
        return lex(new ANTLRInputStream(inputStream));
    }

    private TokenStream lex(CharStream charStream)
    {
        MinstrelLexer lexer = new MinstrelLexer(charStream);
        lexer.addErrorListener(lexErrorListener);
        return new CommonTokenStream(lexer);
    }

    public ParseTree parse(TokenStream lexed)
    {
        MinstrelParser parser = new MinstrelParser(lexed);
        parser.addErrorListener(parseErrorListener);
        return parser.program();
    }

    public Program build(ParseTree parseTree)
    {
        ParseTreeWalker walker = new ParseTreeWalker();
        ASTBuilder builder = new ASTBuilder();
        ASTBuildingListener listener = new ASTBuildingListener(builder);
        walker.walk(listener, parseTree);
        return builder.getProgram();
    }
}
