package org.example.toylanguage.token;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Token {
    private final TokenType type;
    private final String value;
}

