package org.example.toylanguage.expression.value;

import lombok.Getter;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.MemoryScope;
import org.example.toylanguage.context.definition.ClassDefinition;

import java.util.Iterator;
import java.util.stream.Collectors;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

@Getter
public class ClassValue extends IterableValue<ClassDefinition> {
    private final MemoryScope memoryScope;

    public ClassValue(ClassDefinition definition, MemoryScope memoryScope) {
        super(definition);
        this.memoryScope = memoryScope;
    }

    @Override
    public String toString() {
        MemoryContext.pushScope(memoryScope);
        try {
            return getValue().getArguments().stream()
                    .map(t -> t + " = " + getValue(t))
                    .collect(Collectors.joining(", ", getValue().getName() + " [ ", " ]"));
        } finally {
            MemoryContext.endScope();
        }
    }

    public Value<?> getValue(String name) {
        MemoryContext.pushScope(memoryScope);
        try {
            Value<?> result = MemoryContext.getScope().getLocal(name);
            return result != null ? result : NULL_INSTANCE;
        } finally {
            MemoryContext.endScope();
        }
    }

    public void setValue(String name, Value<?> value) {
        MemoryContext.pushScope(memoryScope);
        try {
            MemoryContext.getScope().setLocal(name, value);
        } finally {
            MemoryContext.endScope();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        ClassValue oValue = (ClassValue) o;

        return getValue()
                .getArguments()
                .stream()
                .allMatch(e -> getValue(e).equals(oValue.getValue(e)));
    }

    @Override
    public Iterator<Value<?>> iterator() {
        return getValue()
                .getArguments()
                .stream()
                .<Value<?>>map(this::getValue)
                .iterator();
    }
}
