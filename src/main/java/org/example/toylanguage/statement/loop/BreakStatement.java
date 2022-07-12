package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.BreakContext;
import org.example.toylanguage.statement.Statement;

public class BreakStatement implements Statement {
    @Override
    public void execute() {
        BreakContext.getScope().invoke();
    }
}
