package org.example.toylanguage;

import org.example.toylanguage.exception.TokenException;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalParser {
    private final List<Token> tokens;
    private final String source;

    public LexicalParser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    public List<Token> parse() {
        int position = 0;
        while (position < source.length()) {
            position += nextToken(position);
        }
        return tokens;
    }

    private int nextToken(int position) {
        String nextToken = source.substring(position);

        for (TokenType tokenType : TokenType.values()) {
            Pattern pattern = Pattern.compile("^" + tokenType.getRegex());
            Matcher matcher = pattern.matcher(nextToken);
            if (matcher.find()) {
                if (tokenType != TokenType.Whitespace) {
                    // group(1) is used to get text literal without double quotes
                    String value = matcher.groupCount() > 0 ? matcher.group(1) : matcher.group();
                    Token token = Token.builder().type(tokenType).value(value).build();
                    tokens.add(token);
                }
                return matcher.group().length();
            }
        }

        throw new TokenException(String.format("invalid expression: `%s`", nextToken));
    }

}
