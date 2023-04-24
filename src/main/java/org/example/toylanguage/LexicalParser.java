package org.example.toylanguage;

import org.example.toylanguage.exception.SyntaxException;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transforming the source code into tokens
 * <p>
 *
 * @see Token
 * @see TokenType
 */
public class LexicalParser {
    /**
     * Accumulated tokens
     */
    private final List<Token> tokens;
    /**
     * Source code
     */
    private final String source;
    /**
     * Current row number
     */
    private int rowNumber;

    /**
     * Parse incoming sourceCode into List of lexemes following the TokenType
     */
    public static List<Token> parse(String sourceCode) {
        LexicalParser parser = new LexicalParser(sourceCode);
        parser.parse();
        return parser.tokens;
    }

    private LexicalParser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.rowNumber = 1;
    }

    private void parse() {
        int position = 0; // position in the source code
        while (position < source.length()) {
            // read a lexeme and skip its length
            position += nextToken(position);
        }
    }

    // find the next token
    // returns number of chars that have been parsed to find a lexeme including length of the found lexeme
    private int nextToken(int position) {
        String nextToken = source.substring(position);

        for (TokenType tokenType : TokenType.values()) {
            Pattern pattern = Pattern.compile("^" + tokenType.getRegex());
            Matcher matcher = pattern.matcher(nextToken);
            if (matcher.find()) {
                if (tokenType != TokenType.Whitespace) {
                    // group(1) is used to get text literal without double quotes
                    String value = matcher.groupCount() > 0 ? matcher.group(1) : matcher.group();
                    Token token = Token.builder().type(tokenType).value(value).rowNumber(rowNumber).build();
                    tokens.add(token);

                    if (tokenType == TokenType.LineBreak) {
                        rowNumber++;
                    }
                }

                return matcher.group().length();
            }
        }

        throw new SyntaxException(String.format("Invalid expression at line %d", rowNumber));
    }

}
