package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    Comment("\\#.*"),
    LineBreak("[\\n\\r]"),
    Whitespace("[\\s\\t]"),
    Keyword("(if|elif|else|then|end|print|input|struct|fun|return|loop|in|by|break|next)(?=\\s|$)"),
    GroupDivider("(\\[|\\]|\\,|\\{|}|[.]{2})"),
    Logical("(true|false)(?=\\s|$)"),
    Numeric("([-]?(?=[.]?[0-9])[0-9]*[.]?[0-9]*)"),
    Null("(null)(?=,|\\s|$)"),
    Text("\"([^\"]*)\""),
    Operator("([+]|[-]|[*]|[/]{1,2}|[%]|>=|>|<=|[<]{1,2}|[=]{1,2}|!=|[!]|[:]{2}|[(]|[)]|(new|and|or)(?=\\s|$))"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

