package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.StatementParser;
import org.example.toylanguage.context.definition.ClassDefinition;
import org.example.toylanguage.token.Token;

/**
 * Statement for constructor
 * <p>
 *
 * @see ClassDefinition
 * @see StatementParser#parseClassDefinition(Token)
 */
@Getter
public class ClassStatement extends CompositeStatement {
    private final Integer rowNumber;

    public ClassStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
        this.rowNumber = rowNumber;
    }
}
