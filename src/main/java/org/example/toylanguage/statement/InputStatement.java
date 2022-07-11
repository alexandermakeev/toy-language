package org.example.toylanguage.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.AssignmentOperator;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.token.TokenType;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class InputStatement implements Statement {
    private final String name;
    private final Supplier<String> consoleSupplier;

    @Override
    public void execute() {
        System.out.printf("enter \"%s\" >>> ", name.replace("_", " "));
        String line = consoleSupplier.get();

        Value<?> value;
        if (line.matches(TokenType.Numeric.getRegex())) {
            value = new NumericValue(Double.parseDouble(line));
        } else if (line.matches(TokenType.Logical.getRegex())) {
            value = new LogicalValue(Boolean.valueOf(line));
        } else {
            value = new TextValue(line);
        }

        VariableExpression key = new VariableExpression(name);
        AssignmentOperator assignmentOperator = new AssignmentOperator(key, value);
        assignmentOperator.evaluate();
    }
}
