package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.statement.CompositeStatement;
import org.example.toylanguage.statement.Statement;

public abstract class AbstractLoopStatement extends CompositeStatement {
    protected abstract void init();

    protected abstract boolean hasNext();

    protected abstract void increment();

    @Override
    public void execute() {
        MemoryContext.newScope();
        try {
            init();

            while (hasNext()) {
                MemoryContext.newScope();

                try {
                    for (Statement statement : getStatements2Execute()) {
                        statement.execute();

                        //stop the execution in case ReturnStatement has been invoked
                        if (ReturnContext.getScope().isInvoked())
                            return;
                    }
                } finally {
                    MemoryContext.endScope();

                    increment();
                }

            }
        } finally {
            MemoryContext.endScope();
        }
    }
}