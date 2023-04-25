package org.example.toylanguage.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.LexicalParser;

/**
 * Lexeme types with matching regex expression
 * <p>
 *
 * @see Token
 * @see LexicalParser
 */
@RequiredArgsConstructor
@Getter
public enum TokenType {
    /**
     * Comment
     */
    Comment("\\#.*"),
    /**
     * Line break
     */
    LineBreak("[\\n\\r]"),
    /**
     * Whitespace
     */
    Whitespace("[\\s\\t]"),
    /**
     * Words with a specific sense assigned by the compiler
     * <p>
     * 1. Conditions: <strong>if, elif, else, end</strong>
     * <p>
     * 2. Printing to a console: <strong>print</strong>
     * <p>
     * 4. Defining a class: <strong>class</strong>
     * <p>
     * 5. Defining a function: <strong>fun, return</strong>
     * <p>
     * 6. Loops: <strong>loop, in, by, break, next</strong>
     * <p>
     * 7. Asserting a value: <strong>assert</strong>
     * <p>
     * 8. Raising and handling exceptions: <strong>raise, begin, rescue, ensure</strong>
     */
    Keyword("(if|elif|else|end|print|input|class|fun|return|loop|in|by|break|next|assert|raise|begin|rescue|ensure)(?=\\s|$)(?!_)"),
    /**
     * Dividers for the different lexeme groups
     * <p>
     * 1. Defining a class or a function properties: <strong>[ ]</strong>
     * <p>
     * 2. Counting multiple values: <strong>,</strong>
     * <p>
     * 3. Defining an array values: <strong>{ }</strong>
     * <p>
     * 4. Splitting a loop range: <strong>..</strong>
     * <p>
     * 5. Splitting Derived class from inherited Base types: <strong>:</strong>
     */
    GroupDivider("(\\[|\\]|\\,|\\{|}|\\.{2}|(\\:(?!\\:)))"),
    /**
     * Logical
     */
    Logical("(true|false)(?=\\s|$)(?!_)"),
    /**
     * Numeric
     */
    Numeric("([-]?(?=[.]?[0-9])[0-9]*(?![.]{2})[.]?[0-9]*)"),
    /**
     * Null
     */
    Null("(null)(?=,|\\s|$)(?!_)"),
    /**
     * This reference
     */
    This("(this)(?=,|\\s|$)(?!_)"),
    /**
     * Text value in quotes
     */
    Text("\"([^\"]*)\""),
    /**
     * Operators
     * <p>
     * 1. Addition <strong>+</strong>
     * <p>
     * 2. Subtraction <strong>-</strong>
     * <p>
     * 3. Multiplication <strong>*</strong> and Exponentiation <strong>**</strong>
     * <p>
     * 4. Division <strong>/</strong> and Floor division <strong>//</strong>
     * <p>
     * 5. Modulo <strong>%</strong>
     * <p>
     * 6. Greater than <strong>></strong>
     * <p>
     * 7. Greater than or equal to <strong>>=</strong>
     * <p>
     * 8. Less than <strong><</strong>
     * <p>
     * 9. Less than or equal to <strong><=</strong>
     * <p>
     * 10. Append a value to array <strong><<</strong>
     * <p>
     * 11. Equal <strong>=</strong>
     * <p>
     * 12. Equal to <strong>==</strong> and not equal to <strong>!=</strong>
     * <p>
     * 13. Not operator <strong>!</strong>
     * <p>
     * 14. Creating an instance of a nested class: <strong>:: new</strong>
     * <p>
     * 15. Accessing class's property or class's function: <strong>::</strong>
     * <p>
     * 16. Creating an instance of a class: <strong>new</strong>
     * <p>
     * 17. And operator <strong>and</strong>
     * <p>
     * 18. Or operator <strong>or</strong>
     * <p>
     * 19. Cast operator <strong>as</strong>
     * <p>
     * 20. Instance of operator <strong>is</strong>
     */
    Operator("(\\+|-|\\*{1,2}|/{1,2}|%|>=?|<=|<{1,2}|={1,2}|!=|!|:{2}\\s+new|:{2}|\\(|\\)|(new|and|or|as|is)(?=\\s|$)(?!_))"),
    /**
     * Variable
     */
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

