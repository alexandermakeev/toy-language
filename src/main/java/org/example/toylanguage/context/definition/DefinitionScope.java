package org.example.toylanguage.context.definition;

import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Contains structures (classes, functions) defined in a block of code
 * <p>
 *
 * @see ClassDefinition
 * @see FunctionDefinition
 * @see DefinitionContext
 */
public class DefinitionScope {
    /**
     * Classes defined in the block
     */
    private final Set<ClassDefinition> classes;
    /**
     * Functions declared in the block
     */
    private final Set<FunctionDefinition> functions;
    /**
     * Parent DefinitionScope to access the structures defined in outer blocks of code
     */
    @Getter
    private final DefinitionScope parent;

    public DefinitionScope(DefinitionScope parent) {
        this.classes = new HashSet<>();
        this.functions = new HashSet<>();
        this.parent = parent;
    }

    /**
     * Get ClassDefinition from the current block and from outer blocks of code
     *
     * @param name name of the class
     */
    public ClassDefinition getClass(String name) {
        Optional<ClassDefinition> classDefinition = classes.stream()
                .filter(t -> t.getClassDetails().getName().equals(name))
                .findAny();
        if (classDefinition.isPresent())
            return classDefinition.get();
        else if (parent != null)
            return parent.getClass(name);
        else
            return null;
    }

    /**
     * Add ClassDefinition to the current block
     */
    public void addClass(ClassDefinition classDefinition) {
        classes.add(classDefinition);
    }

    /**
     * Get FunctionDefinition from the current block and from outer blocks of code
     *
     * @param name          name of the function
     * @param argumentsSize count of function arguments, useful in case there are multiple functions with the same name but with different length of arguments declared
     */
    public FunctionDefinition getFunction(String name, int argumentsSize) {
        Optional<FunctionDefinition> functionDefinition = functions.stream()
                .filter(t -> Objects.equals(t.getDetails().getName(), name)
                        && Objects.equals(t.getDetails().getArguments().size(), argumentsSize))
                .findAny();
        if (functionDefinition.isPresent())
            return functionDefinition.get();
        else if (parent != null)
            return parent.getFunction(name, argumentsSize);
        else
            return null;
    }

    /**
     * Check that DefinitionScope contains the function
     *
     * @param name          name of the function
     * @param argumentsSize amount of function arguments in case there are multiple functions with the same name declared
     */
    public boolean containsFunction(String name, int argumentsSize) {
        return getFunction(name, argumentsSize) != null;
    }

    /**
     * Add FunctionDefinition to the current block
     */
    public void addFunction(FunctionDefinition functionDefinition) {
        functions.add(functionDefinition);
    }
}
