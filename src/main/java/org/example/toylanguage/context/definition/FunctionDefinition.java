package org.example.toylanguage.context.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.StatementParser;
import org.example.toylanguage.statement.FunctionStatement;
import org.example.toylanguage.token.Token;

/**
 * Definition for a function
 * <p>
 *
 * @see StatementParser#parseFunctionDefinition(Token)
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FunctionDefinition implements Definition {
    /**
     * Details for a function
     */
    @EqualsAndHashCode.Include
    private final FunctionDetails details;
    /**
     * Statement(s) defined in the function body
     */
    private final FunctionStatement statement;
    /**
     * Contains nested classes and functions defined in this function
     */
    private final DefinitionScope definitionScope;
}
