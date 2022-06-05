package org.example.toylanguage.expression;

import lombok.Getter;
import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.expression.value.StructureValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class StructureExpression implements Expression {
    private final StructureDefinition definition;
    private final Map<String, Expression> arguments;

    public StructureExpression(StructureDefinition definition, List<Expression> arguments) {
        this.definition = definition;
        this.arguments = IntStream.range(0, arguments.size())
                .boxed()
                .collect(Collectors.toMap(i -> definition.getArguments().get(i), arguments::get));
    }

    @Override
    public Value<?> evaluate() {
        return new StructureValue(this);
    }
}
