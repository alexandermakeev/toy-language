package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    LineBreak("[\\n\\r]"),
    Whitespace("[\\s\\t]"),
    Keyword("(if|then|end|print|input|struct|arg)"),
    GroupDivider("(\\[|\\]|\\,)"),
    Logical("true|false"),
    Numeric("[0-9]+"),
    Text("\"([^\"]*)\""),
    Operator("(\\+|\\-|\\*|\\/|\\>|\\<|\\={1,2}|\\!|\\:{2}|\\(|\\)|new)"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

