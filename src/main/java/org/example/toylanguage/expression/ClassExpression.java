package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.ClassInstanceContext;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.MemoryScope;
import org.example.toylanguage.context.ValueReference;
import org.example.toylanguage.context.definition.ClassDefinition;
import org.example.toylanguage.context.definition.DefinitionContext;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.NullValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.ClassStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public class ClassExpression implements Expression {
    private final String name;
    private final List<? extends Expression> argumentExpressions;
    // super classes and subclasses available to a class instance
    private final Map<String, ClassValue> relations;

    public ClassExpression(String name, List<? extends Expression> argumentExpressions) {
        this(name, argumentExpressions, new HashMap<>());
    }

    @Override
    public Value<?> evaluate() {
        //initialize class arguments
        List<ValueReference> values = argumentExpressions.stream().map(ValueReference::instanceOf).collect(Collectors.toList());
        return evaluate(values);
    }

    /**
     * Evaluate nested class
     *
     * @param classValue instance of the parent class
     */
    public Value<?> evaluate(ClassValue classValue) {
        //initialize class arguments
        List<ValueReference> values = argumentExpressions.stream().map(ValueReference::instanceOf).collect(Collectors.toList());

        //set parent class's definition
        ClassDefinition classDefinition = classValue.getValue();
        DefinitionContext.pushScope(classDefinition.getDefinitionScope());

        try {
            return evaluate(values);
        } finally {
            DefinitionContext.endScope();
        }
    }

    private Value<?> evaluate(List<ValueReference> values) {
        //get class's definition and statement
        ClassDefinition definition = DefinitionContext.getScope().getClass(name);
        ClassStatement classStatement = definition.getStatement();

        //set separate scope
        MemoryScope classScope = new MemoryScope(null);
        MemoryContext.pushScope(classScope);

        //initialize constructor arguments
        ClassValue classValue = new ClassValue(definition, classScope, relations);
        relations.put(name, classValue);

        // fulfill missing properties
        // class A [arg1, arg2]
        // new A [arg1] -> new A [arg1, null]
        List<ValueReference> valuesToSet = IntStream.range(0, definition.getClassDetails().getArguments().size())
                .boxed()
                .map(i -> values.size() > i ? values.get(i) : ValueReference.instanceOf(NullValue.NULL_INSTANCE))
                .collect(Collectors.toList());

        //invoke super constructors and set a ClassValue relation
        definition.getInheritedClasses()
                .stream()
                .map(superType -> {
                    // initialize super class's properties
                    // class A [a_arg]
                    // class B [b_arg1, b_arg2]: A [b_arg1]
                    List<ValueReference> superTypeArguments = superType.getArguments().stream()
                            .map(t -> definition.getClassDetails().getArguments().indexOf(t))
                            .map(valuesToSet::get)
                            .collect(Collectors.toList());
                    return new ClassExpression(superType.getName(), superTypeArguments, relations);
                })
                .forEach(ClassExpression::evaluate);

        try {
            ClassInstanceContext.pushValue(classValue);
            IntStream.range(0, definition.getClassDetails().getArguments().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(definition.getClassDetails().getArguments().get(i), valuesToSet.get(i)));

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
