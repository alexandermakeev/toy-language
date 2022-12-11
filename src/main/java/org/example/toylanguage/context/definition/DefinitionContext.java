package org.example.toylanguage.context.definition;

import java.util.Stack;

public class DefinitionContext {
    private final static Stack<DefinitionScope> scopes = new Stack<>();

    public static DefinitionScope getScope() {
        return scopes.peek();
    }

    public static DefinitionScope newScope() {
        return new DefinitionScope(scopes.isEmpty() ? null : scopes.peek());
    }

    public static void pushScope(DefinitionScope scope) {
        scopes.push(scope);
    }

    public static void endScope() {
        scopes.pop();
    }
}
