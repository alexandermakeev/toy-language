package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.ReturnContext;
import org.example.toylanguage.definition.FunctionDefinition;
import org.example.toylanguage.expression.operator.AssignmentOperator;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.FunctionStatement;

import java.util.List;
import java.util.stream.IntStream;

import static org.example.toylanguage.context.VariableScope.Local;

@RequiredArgsConstructor
@Getter
public class FunctionExpression implements Expression {
    private final FunctionDefinition definition;
    private final List<Expression> values;

    @Override
    public Value<?> evaluate() {
        FunctionStatement statement = definition.getStatement();
        MemoryContext.newScope(); //set new memory scope

        //initialize function arguments
        IntStream.range(0, values.size())
                .boxed()
                .map(i -> new AssignmentOperator(new VariableExpression(definition.getArguments().get(i)), values.get(i), Local))
                .forEach(AssignmentOperator::evaluate);

        //execute function body
        try {
            statement.execute();
            return ReturnContext.getScope().getResult();
        } finally {
            MemoryContext.endScope(); //end function memory scope
            ReturnContext.reset(); //reset return context
        }
    }
}
