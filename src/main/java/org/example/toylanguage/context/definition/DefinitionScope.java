package org.example.toylanguage.context.definition;

import lombok.Getter;
import org.example.toylanguage.exception.SyntaxException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DefinitionScope {
    private final Set<ClassDefinition> classes;
    private final Set<FunctionDefinition> functions;
    @Getter
    private final DefinitionScope parent;

    public DefinitionScope(DefinitionScope parent) {
        this.classes = new HashSet<>();
        this.functions = new HashSet<>();
        this.parent = parent;
    }

    public DefinitionScope() {
        this(null);
    }

    public ClassDefinition getClass(String name) {
        Optional<ClassDefinition> classDefinition = classes.stream()
                .filter(t -> t.getName().equals(name))
                .findAny();
        if (classDefinition.isPresent())
            return classDefinition.get();
        else if (parent != null)
            return parent.getClass(name);
        else
            throw new SyntaxException(String.format("Class is not defined: %s", name));
    }

    public boolean containsClass(String name) {
        if (functions.stream().anyMatch(t -> t.getName().equals(name)))
            return true;
        else
            return parent != null && parent.containsClass(name);
    }

    public void addClass(ClassDefinition classDefinition) {
        classes.add(classDefinition);
    }

    public FunctionDefinition getFunction(String name) {
        Optional<FunctionDefinition> functionDefinition = functions.stream()
                .filter(t -> t.getName().equals(name))
                .findAny();
        if (functionDefinition.isPresent())
            return functionDefinition.get();
        else if (parent != null)
            return parent.getFunction(name);
        else
            throw new SyntaxException(String.format("Function is not defined: %s", name));
    }

    public boolean containsFunction(String name) {
        if (functions.stream().anyMatch(t -> t.getName().equals(name)))
            return true;
        else
            return parent != null && parent.containsFunction(name);
    }

    public void addFunction(FunctionDefinition functionDefinition) {
        functions.add(functionDefinition);
    }
}
