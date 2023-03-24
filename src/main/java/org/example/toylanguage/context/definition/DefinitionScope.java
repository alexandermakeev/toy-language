package org.example.toylanguage.context.definition;

import lombok.Getter;
import org.example.toylanguage.exception.ExecutionException;

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

    public ClassDefinition getClass(String name) {
        Optional<ClassDefinition> classDefinition = classes.stream()
                .filter(t -> t.getClassDetails().getName().equals(name))
                .findAny();
        if (classDefinition.isPresent())
            return classDefinition.get();
        else if (parent != null)
            return parent.getClass(name);
        else
            throw new ExecutionException(String.format("Class is not defined: %s", name));
    }

    public void addClass(ClassDefinition classDefinition) {
        classes.add(classDefinition);
    }

    public FunctionDefinition getFunction(String name, int argumentsSize) {
        Optional<FunctionDefinition> functionDefinition = getFunctionDefinitionOptional(name, argumentsSize);
        if (functionDefinition.isPresent())
            return functionDefinition.get();
        else if (parent != null)
            return parent.getFunction(name, argumentsSize);
        else
            throw new ExecutionException(String.format("Function is not defined: %s", name));
    }

    public boolean containsFunction(String name, int argumentsSize) {
        Optional<FunctionDefinition> functionDefinition = getFunctionDefinitionOptional(name, argumentsSize);
        if (functionDefinition.isPresent())
            return true;
        else if (parent != null)
            return parent.containsFunction(name, argumentsSize);
        else
            return false;
    }

    public void addFunction(FunctionDefinition functionDefinition) {
        functions.add(functionDefinition);
    }

    private Optional<FunctionDefinition> getFunctionDefinitionOptional(String name, int argumentsSize) {
        return functions.stream()
                .filter(t -> t.getName().equals(name) && t.getArguments().size() == argumentsSize)
                .findAny();
    }
}
