package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.Value;

@Getter
public class AssertStatement extends Statement {
    private final Expression expression;

    public AssertStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value instanceof LogicalValue && !((LogicalValue) value).getValue()) {
            ExceptionContext.raiseException("Assertion error");
            ExceptionContext.addTracedStatement(this);
        }
    }
}
