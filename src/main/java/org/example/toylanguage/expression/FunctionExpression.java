package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.toylanguage.context.*;
import org.example.toylanguage.context.definition.*;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.FunctionStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class FunctionExpression implements Expression {
    @ToString.Include
    private final String name;
    private final List<Expression> argumentExpressions;

    @Override
    public Value<?> evaluate() {
        //initialize function arguments
        List<Value<?>> values = new ArrayList<>(argumentExpressions.size());
        for (Expression expression : argumentExpressions) {
            Value<?> value = expression.evaluate();
            if (value == null) return null;
            values.add(value);
        }
        return evaluate(values);
    }

    /**
     * Evaluate class's function
     *
     * @param classValue instance of class where the function is placed in
     */
    public Value<?> evaluate(ClassValue classValue) {
        //initialize function arguments
        List<Value<?>> values = new ArrayList<>(argumentExpressions.size());
        for (Expression expression : argumentExpressions) {
            Value<?> value = expression.evaluate();
            if (value == null) return null;
            values.add(value);
        }

        // find a class containing the function
        ClassDefinition classDefinition = findClassDefinitionContainingFunction(classValue.getValue(), name, values.size());
        if (classDefinition == null) {
            String args = IntStream.range(0, values.size()).mapToObj(t -> "arg" + (t + 1)).collect(Collectors.joining(", "));
            return ExceptionContext.raiseException(String.format("Function `%s#%s [%s]` is not defined",
                    classValue.getValue().getClassDetails().getName(), name, args));
        }
        DefinitionScope classDefinitionScope = classDefinition.getDefinitionScope();
        ClassValue functionClassValue = classValue.getRelation(classDefinition.getClassDetails().getName());
        MemoryScope memoryScope = functionClassValue.getMemoryScope();

        //set class's definition and memory scopes
        DefinitionContext.pushScope(classDefinitionScope);
        MemoryContext.pushScope(memoryScope);
        ClassInstanceContext.pushValue(functionClassValue);

        try {
            //proceed function
            return evaluate(values);
        } finally {
            DefinitionContext.endScope();
            MemoryContext.endScope();
            ClassInstanceContext.popValue();
        }
    }

    private Value<?> evaluate(List<Value<?>> values) {
        //get function's definition and statement
        FunctionDefinition definition = DefinitionContext.getScope().getFunction(name, values.size());
        if (definition == null) {
            String args = IntStream.range(0, values.size()).mapToObj(t -> "arg" + (t + 1)).collect(Collectors.joining(", "));
            return ExceptionContext.raiseException(String.format("Function `%s [%s]` is not defined", name, args));
        }
        FunctionStatement statement = definition.getStatement();
        FunctionDetails details = definition.getDetails();

        //set new memory scope
        MemoryContext.pushScope(MemoryContext.newScope());

        try {
            //initialize function arguments
            IntStream.range(0, details.getArguments().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(details.getArguments().get(i), values.size() > i ? values.get(i) : NullValue.NULL_INSTANCE));

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

    /**
     * Find a Base class that contains the required function
     *
     * <pre>{@code
     * class A
     *      fun action
     *      end
     * end
     *
     * class B
     * end
     *
     * b = new B
     * # Function `action` is not available from the DefinitionScope of class B as it's declared in the class A
     * b :: action []
     *
     * }</pre>
     */
    private ClassDefinition findClassDefinitionContainingFunction(ClassDefinition classDefinition, String functionName, int argumentsSize) {
        DefinitionScope definitionScope = classDefinition.getDefinitionScope();
        if (definitionScope.containsFunction(functionName, argumentsSize)) {
            return classDefinition;
        } else {
            for (ClassDetails baseType : classDefinition.getBaseTypes()) {
                ClassDefinition baseTypeDefinition = definitionScope.getClass(baseType.getName());
                ClassDefinition functionClassDefinition = findClassDefinitionContainingFunction(baseTypeDefinition, functionName, argumentsSize);
                if (functionClassDefinition != null)
                    return functionClassDefinition;
            }
            return null;
        }
    }
}
