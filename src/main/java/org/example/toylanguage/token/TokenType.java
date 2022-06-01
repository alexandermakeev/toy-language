package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    Comment("\\#.*"),
    LineBreak("[\\n\\r]"),
    Whitespace("[\\s\\t]"),
    Keyword("(if|then|end|print|input|struct|fun|return)(?=\\s|$)"),
    GroupDivider("(\\[|\\]|[,])"),
    Logical("(true|false)(?=\\s|$)"),
    Numeric("[+-]?((?=[.]?[0-9])[0-9]*[.]?[0-9]*)"),
    Text("\"([^\"]*)\""),
    Operator("([+]|[-]|[*]|[/]|[>]|[<]|[=]{1,2}|[!]|[:]{2}|[(]|[)]|new(?=\\s|$))"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

