package org.example.toylanguage.context;

import java.util.Stack;

/**
 * Associates a given {@link MemoryScope} with isolated block of code
 */
public class MemoryContext {
    private static final Stack<MemoryScope> scopes = new Stack<>();

    /**
     * Get scope of the current block
     */
    public static MemoryScope getScope() {
        return scopes.peek();
    }

    /**
     * Create and set a new MemoryScope to enter a nested block
     */
    public static MemoryScope newScope() {
        return new MemoryScope(scopes.isEmpty() ? null : scopes.peek());
    }

    /**
     * Set an existing scope to enter any block
     */
    public static void pushScope(MemoryScope scope) {
        scopes.push(scope);
    }

    /**
     * Terminate the current scope to exit block
     */
    public static void endScope() {
        scopes.pop();
    }
}
