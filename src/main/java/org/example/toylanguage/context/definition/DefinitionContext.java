package org.example.toylanguage.context.definition;

import java.util.Stack;

/**
 * Associates a given {@link DefinitionScope} with isolated block of code
 * <p>
 *
 * @see DefinitionScope
 * @see ClassDefinition
 * @see FunctionDefinition
 */
public class DefinitionContext {
    private final static Stack<DefinitionScope> scopes = new Stack<>();

    /**
     * Get scope of the current block
     */
    public static DefinitionScope getScope() {
        return scopes.peek();
    }

    /**
     * Create and set a new DefinitionScope to enter a nested block
     */
    public static DefinitionScope newScope() {
        return new DefinitionScope(scopes.isEmpty() ? null : scopes.peek());
    }

    /**
     * Set an existing scope to enter any block
     */
    public static void pushScope(DefinitionScope scope) {
        scopes.push(scope);
    }

    /**
     * Terminate the current scope to exit block
     */
    public static void endScope() {
        scopes.pop();
    }
}
