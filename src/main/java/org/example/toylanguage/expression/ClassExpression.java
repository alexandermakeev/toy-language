package org.example.toylanguage.expression;

import lombok.Getter;
import org.example.toylanguage.context.definition.ClassDefinition;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class ClassExpression implements Expression {
    private final ClassDefinition definition;
    private final Map<String, Expression> arguments;

    public ClassExpression(ClassDefinition definition, List<Expression> arguments) {
        this.definition = definition;
        this.arguments = IntStream.range(0, arguments.size())
                .boxed()
                .collect(Collectors.toMap(i -> definition.getArguments().get(i), arguments::get));
    }

    @Override
    public Value<?> evaluate() {
        return new ClassValue(this);
    }
}
