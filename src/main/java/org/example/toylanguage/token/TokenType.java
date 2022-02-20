package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    Whitespace("[\\s\\t\\n\\r]"),
    Keyword("(if|then|end|print|input|struct|arg|new)"),
    GroupDivider("(\\[|\\])"),
    Logical("true|false"),
    Numeric("[0-9]+"),
    Text("\"([^\"]*)\""),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*"),
    Operator("(\\+{1,2}|\\-{1,2}|\\>|\\<|\\={1,2}|\\!|\\:{2})");

    private final String regex;
}

