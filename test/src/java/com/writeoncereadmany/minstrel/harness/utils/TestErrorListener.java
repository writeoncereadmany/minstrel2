package com.writeoncereadmany.minstrel.harness.utils;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class TestErrorListener implements ANTLRErrorListener
{
    private List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int line, int column, String s, RecognitionException e)
    {
        errors.add("syntax error at line " + line + ", column " + column + ": " +  s);
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet)
    {
        throw new IllegalStateException("Poop!");
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet)
    {
        throw new IllegalStateException("Fart!");
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet)
    {
        throw new IllegalStateException("Weewee!");
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> errors()
    {
        return errors;
    }
}
