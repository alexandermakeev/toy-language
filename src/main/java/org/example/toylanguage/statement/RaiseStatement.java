package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;

@Getter
public class RaiseStatement extends Statement {
    private final Expression expression;

    public RaiseStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        if (value == null) return;
        if (value == NullValue.NULL_INSTANCE) {
            value = new TextValue("Empty exception");
        }
        ExceptionContext.raiseException(value);
        ExceptionContext.addTracedStatement(this);
    }
}
