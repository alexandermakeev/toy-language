package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.context.definition.FunctionDefinition;
import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.FunctionStatement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public class FunctionExpression implements Expression {
    private final FunctionDefinition definition;
    private final List<Expression> arguments;

    @Override
    public Value<?> evaluate() {
        FunctionStatement statement = definition.getStatement();

        //initialize function arguments
        List<Value<?>> values = arguments.stream().map(Expression::evaluate).collect(Collectors.toList());

        //set new memory scope
        MemoryContext.pushScope(MemoryContext.newScope());

        try {
            //initialize function arguments
            IntStream.range(0, definition.getArguments().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(definition.getArguments().get(i), values.size() > i ? values.get(i) : NullValue.NULL_INSTANCE));

            //execute function body
            statement.execute();

            //obtain function result
            return ReturnContext.getScope().getResult();
        } finally {
            // release function memory and return context
            MemoryContext.endScope();
            ReturnContext.reset();
        }
    }
}
