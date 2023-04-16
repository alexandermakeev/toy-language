package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.context.MemoryContext;

@Getter
public class HandleExceptionStatement extends Statement {
    private final CompositeStatement beginStatement;
    private final CompositeStatement rescueStatement;
    private final CompositeStatement ensureStatement;
    private final String errorVariable;

    public HandleExceptionStatement(Integer rowNumber, String blockName, CompositeStatement beginStatement,
                                    CompositeStatement rescueStatement, CompositeStatement ensureStatement, String errorVariable) {
        super(rowNumber, blockName);
        this.beginStatement = beginStatement;
        this.rescueStatement = rescueStatement;
        this.ensureStatement = ensureStatement;
        this.errorVariable = errorVariable;
    }

    @Override
    public void execute() {
        MemoryContext.pushScope(MemoryContext.newScope());
        try {
            beginStatement.execute();
        } finally {
            MemoryContext.endScope();
        }

        // rescue block
        if (rescueStatement != null && ExceptionContext.isRaised()) {

            MemoryContext.pushScope(MemoryContext.newScope());
            if (errorVariable != null) {
                MemoryContext.getScope().setLocal(errorVariable, ExceptionContext.getException().getValue());
            }

            ExceptionContext.rescueException();

            try {
                rescueStatement.execute();
            } finally {
                MemoryContext.endScope();
            }
        }

        // ensure block
        if (ensureStatement != null) {
            boolean raised = ExceptionContext.isRaised();
            if (raised) {
                // ensure block shouldn't accumulate stack trace
                ExceptionContext.disable();
            }

            MemoryContext.pushScope(MemoryContext.newScope());
            try {
                ensureStatement.execute();
            } finally {
                MemoryContext.endScope();
                if (raised) {
                    // continue to accumulate stack trace
                    ExceptionContext.enable();
                }
            }
        }
    }
}
