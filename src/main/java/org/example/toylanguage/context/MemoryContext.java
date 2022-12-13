package org.example.toylanguage.context;

import java.util.Stack;

/**
 * Memory management to isolate defined variables
 *
 * @see MemoryScope
 */
public class MemoryContext {
    private static final Stack<MemoryScope> scopes = new Stack<>();

    public static MemoryScope getScope() {
        return scopes.peek();
    }

    public static MemoryScope newScope() {
        return new MemoryScope(scopes.isEmpty() ? null : scopes.peek());
    }

    public static void pushScope(MemoryScope scope) {
        scopes.push(scope);
    }

    public static void endScope() {
        scopes.pop();
    }
}
