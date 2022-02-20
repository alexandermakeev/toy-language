package org.example.toylanguage;

import org.example.toylanguage.token.Token;

import java.util.ArrayList;
import java.util.List;

public class LexicalParser {
    private final List<Token> tokens;
    private final String source;

    public LexicalParser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    public List<Token> parse() {
        return tokens;
    }
}
