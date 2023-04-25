package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.token.TokenType;

import java.util.function.Supplier;

@Getter
public class InputStatement extends Statement {
    private final String name;
    private final Supplier<String> consoleSupplier;

    public InputStatement(Integer rowNumber, String blockName, String name, Supplier<String> consoleSupplier) {
        super(rowNumber, blockName);
        this.name = name;
        this.consoleSupplier = consoleSupplier;
    }

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

        MemoryContext.getScope().set(name, value);
    }
}
