package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@Getter
public class PrintStatement extends Statement {
    private final Expression expression;

    public PrintStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value != null) {
            System.out.println(value);
        }
        ExceptionContext.addTracedStatement(this);
    }
}
