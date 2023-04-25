package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.NextContext;
import org.example.toylanguage.statement.Statement;

public class NextStatement extends Statement {
    public NextStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }

    @Override
    public void execute() {
        NextContext.getScope().invoke();
    }
}
