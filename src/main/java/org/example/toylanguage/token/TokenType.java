package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    Comment("\\#.*"),
    LineBreak("[\\n\\r]"),
    Whitespace("[\\s\\t]"),
    Keyword("(if|elif|else|end|print|input|class|fun|return|loop|in|by|break|next|assert|raise|begin|rescue|ensure)(?=\\s|$)(?!_)"),
    GroupDivider("(\\[|\\]|\\,|\\{|}|\\.{2}|(\\:(?!\\:)))"),
    Logical("(true|false)(?=\\s|$)(?!_)"),
    Numeric("([-]?(?=[.]?[0-9])[0-9]*(?![.]{2})[.]?[0-9]*)"),
    Null("(null)(?=,|\\s|$)(?!_)"),
    This("(this)(?=,|\\s|$)(?!_)"),
    Text("\"([^\"]*)\""),
    Operator("(\\+|-|\\*{1,2}|/{1,2}|%|>=|>|<=|<{1,2}|={1,2}|!=|!|:{2}\\s+new|:{2}|\\(|\\)|(new|and|or|as|is)(?=\\s|$)(?!_))"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

