package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.NextContext;
import org.example.toylanguage.statement.Statement;

public class NextStatement implements Statement {
    @Override
    public void execute() {
        NextContext.getScope().invoke();
    }
}
