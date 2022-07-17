package org.example.toylanguage.statement.loop;

import org.example.toylanguage.context.BreakContext;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.NextContext;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.statement.CompositeStatement;
import org.example.toylanguage.statement.Statement;

public abstract class AbstractLoopStatement extends CompositeStatement {
    protected abstract void init();

    protected abstract boolean hasNext();

    protected abstract void preIncrement();

    protected abstract void postIncrement();

    @Override
    public void execute() {
        // memory scope for counter variables
        MemoryContext.newScope();
        try {

            // init loop
            init();

            while (hasNext()) {
                preIncrement();

                // isolated memory scope for each iteration
                MemoryContext.newScope();

                try {

                    // execute inner statements
                    for (Statement statement : getStatements2Execute()) {
                        statement.execute();

                        // stop the execution in case ReturnStatement has been invoked
                        if (ReturnContext.getScope().isInvoked())
                            return;

                        // stop the execution in case BreakStatement has been invoked
                        if (BreakContext.getScope().isInvoked())
                            return;

                        // jump to the next iteration in case NextStatement has been invoked
                        if (NextContext.getScope().isInvoked())
                            break;
                    }
                } finally {
                    NextContext.reset();
                    MemoryContext.endScope(); // release each iteration memory

                    // increment the counter even if the NextStatement has been called
                    postIncrement();
                }

            }
        } finally {
            MemoryContext.endScope(); // release loop memory
            BreakContext.reset();
        }
    }
}
