package org.example.toylanguage;

import org.example.toylanguage.statement.Statement;
import org.example.toylanguage.token.Token;

import java.util.List;

public class StatementParser {
    private final List<Token> tokens;
    private int position;

    public StatementParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Statement parse() {
        // TODO: 2/20/22
        return null;
    }
}
