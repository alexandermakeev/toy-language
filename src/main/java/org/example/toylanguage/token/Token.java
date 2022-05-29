package org.example.toylanguage.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Token {
    private final TokenType type;
    private final String value;
    private final Integer row;
}

