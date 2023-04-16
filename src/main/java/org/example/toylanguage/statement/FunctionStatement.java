package org.example.toylanguage.statement;

import lombok.Getter;

@Getter
public class FunctionStatement extends CompositeStatement {
    public FunctionStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }
}
