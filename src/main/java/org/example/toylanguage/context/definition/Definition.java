package org.example.toylanguage.context.definition;

/**
 * Interface to specify structures supported by toy-language
 * <p>
 *
 * @see ClassDefinition
 * @see FunctionDefinition
 */
public interface Definition {
    /**
     * Contains nested structures declared in this definition
     */
    DefinitionScope getDefinitionScope();
}
