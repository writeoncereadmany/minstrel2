package com.writeoncereadmany.minstrel.parser;

import com.writeoncereadmany.minstrel.generated.grammar.MinstrelLexer;
import com.writeoncereadmany.minstrel.generated.grammar.MinstrelParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;

public class MinstrelLauncher
{
    private final ANTLRErrorListener lexErrorListener;
    private final ANTLRErrorListener parseErrorListener;

    public MinstrelLauncher(ANTLRErrorListener lexErrorListener, ANTLRErrorListener parseErrorListener) {
        this.lexErrorListener = lexErrorListener;
        this.parseErrorListener = parseErrorListener;
    }

    public TokenStream lex(InputStream inputStream) throws IOException
    {
        return lex(new ANTLRInputStream(inputStream));
    }

    public TokenStream lex(String programText)
    {
        return lex(new ANTLRInputStream(programText));
    }

    private TokenStream lex(CharStream charStream) {
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
}
