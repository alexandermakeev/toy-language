package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.BreakContext;
import org.example.toylanguage.statement.Statement;

public class BreakStatement extends Statement {
    public BreakStatement(Integer rowNumber, String blockName) {
        super(rowNumber, blockName);
    }

    @Override
    public void execute() {
        BreakContext.getScope().invoke();
    }
}
