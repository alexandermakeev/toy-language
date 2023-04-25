package org.example.toylanguage.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Token (lexeme) details
 */
@Builder
@Getter
@ToString
public class Token {
    /**
     * Type of the token
     */
    private final TokenType type;
    /**
     * Value of the token
     */
    private final String value;
    /**
     * Row number where the token is defined
     */
    private final Integer rowNumber;
}

