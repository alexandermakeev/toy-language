package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

@Getter
public class ReturnStatement extends Statement {
    private final Expression expression;

    public ReturnStatement(Integer rowNumber, String blockName, Expression expression) {
        super(rowNumber, blockName);
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> result = expression.evaluate();
        if (result != null) {
            ReturnContext.getScope().invoke(result);
        }
        ExceptionContext.addTracedStatement(this);
    }
}
