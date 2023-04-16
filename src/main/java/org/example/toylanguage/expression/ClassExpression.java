package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.context.*;
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
    private final List<? extends Expression> propertiesExpressions;
    // contains Derived class and all the Base classes chain that Derived class inherits
    private final Map<String, ClassValue> relations;

    public ClassExpression(String name, List<? extends Expression> propertiesExpressions) {
        this(name, propertiesExpressions, new HashMap<>());
    }

    @Override
    public Value<?> evaluate() {
        //initialize class's properties
        List<ValueReference> values = propertiesExpressions.stream().map(ValueReference::instanceOf).collect(Collectors.toList());
        return evaluate(values);
    }

    /**
     * Evaluate nested class
     *
     * @param classValue instance of the parent class
     */
    public Value<?> evaluate(ClassValue classValue) {
        //initialize class's properties
        List<ValueReference> values = propertiesExpressions.stream().map(ValueReference::instanceOf).collect(Collectors.toList());

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

        // fill the missing properties with NullValue.NULL_INSTANCE
        // class A [arg1, arg2]
        // new A [arg1] -> new A [arg1, null]
        // new A [arg1, arg2, arg3] -> new A [arg1, arg2]
        List<ValueReference> valuesToSet = IntStream.range(0, definition.getClassDetails().getProperties().size())
                .boxed()
                .map(i -> values.size() > i ? values.get(i) : ValueReference.instanceOf(NullValue.NULL_INSTANCE))
                .collect(Collectors.toList());

        //invoke constructors of the base classes and set a ClassValue relation
        definition.getBaseTypes()
                .stream()
                .map(baseType -> {
                    // initialize base class's properties
                    // class A [a_arg]
                    // class B [b_arg1, b_arg2]: A [b_arg1]
                    List<ValueReference> baseClassProperties = baseType.getProperties().stream()
                            .map(t -> definition.getClassDetails().getProperties().indexOf(t))
                            .map(valuesToSet::get)
                            .collect(Collectors.toList());
                    return new ClassExpression(baseType.getName(), baseClassProperties, relations);
                })
                .forEach(ClassExpression::evaluate);

        try {
            ClassInstanceContext.pushValue(classValue);
            IntStream.range(0, definition.getClassDetails().getProperties().size()).boxed()
                    .forEach(i -> MemoryContext.getScope()
                            .setLocal(definition.getClassDetails().getProperties().get(i), valuesToSet.get(i)));

            //execute function body
            DefinitionContext.pushScope(definition.getDefinitionScope());
            try {
                classStatement.execute();
            } finally {
                DefinitionContext.endScope();
            }

            if (ExceptionContext.isRaised()) return null;

            return classValue;
        } finally {
            MemoryContext.endScope();
            ClassInstanceContext.popValue();
        }
    }
}
