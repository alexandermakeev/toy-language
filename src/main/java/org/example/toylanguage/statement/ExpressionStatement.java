package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.expression.Expression;

@Getter
public class ExpressionStatement extends Statement {
    private final Expression expression;

    public ExpressionStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        expression.evaluate();
        ExceptionContext.addTracedStatement(this);
    }
}
