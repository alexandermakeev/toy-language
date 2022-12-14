package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.ClassInstanceContext;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.MemoryScope;
import org.example.toylanguage.context.definition.ClassDefinition;
import org.example.toylanguage.context.definition.DefinitionContext;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.ClassStatement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public class ClassExpression implements Expression {
    private final String name;
    private final List<Expression> argumentExpressions;

    @Override
    public Value<?> evaluate() {
        //initialize class arguments
        List<Value<?>> values = argumentExpressions.stream().map(Expression::evaluate).collect(Collectors.toList());

        //get class's definition and statement
        ClassDefinition definition = DefinitionContext.getScope().getClass(name);
        ClassStatement classStatement = definition.getStatement();

        //set separate scope
        MemoryScope classScope = new MemoryScope(null);
        MemoryContext.pushScope(classScope);

        try {
            //initialize constructor arguments
            ClassValue classValue = new ClassValue(definition, classScope);
            ClassInstanceContext.pushValue(classValue);
            IntStream.range(0, definition.getArguments().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(definition.getArguments().get(i), values.size() > i ? values.get(i) : NullValue.NULL_INSTANCE));

            //execute function body
            DefinitionContext.pushScope(definition.getDefinitionScope());
            try {
                classStatement.execute();
            } finally {
                DefinitionContext.endScope();
            }

            return classValue;
        } finally {
            MemoryContext.endScope();
            ClassInstanceContext.popValue();
        }
    }
}
