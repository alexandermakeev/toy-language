package org.example.toylanguage;

import org.example.toylanguage.exception.TokenException;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LexicalParser {
    private final List<Token> tokens;
    private final String source;
    private final List<Integer> linesIndices;

    public LexicalParser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.linesIndices = IntStream
                .iterate(source.indexOf("\n"), index -> index >= 0, index -> source.indexOf("\n", index + 1))
                .boxed()
                .collect(Collectors.toList());
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

        int row = IntStream.range(0, linesIndices.size())
                .filter(i -> position <= linesIndices.get(i))
                .findFirst()
                .orElse(linesIndices.size()) + 1;

        for (TokenType tokenType : TokenType.values()) {
            Pattern pattern = Pattern.compile("^" + tokenType.getRegex());
            Matcher matcher = pattern.matcher(nextToken);
            if (matcher.find()) {
                if (tokenType != TokenType.Whitespace) {
                    // group(1) is used to get text literal without double quotes
                    String value = matcher.groupCount() > 0 ? matcher.group(1) : matcher.group();
                    Token token = Token.builder().type(tokenType).value(value).row(row).build();
                    tokens.add(token);
                }
                return matcher.group().length();
            }
        }

        throw new TokenException(String.format("invalid expression at line %d", row));
    }

}
