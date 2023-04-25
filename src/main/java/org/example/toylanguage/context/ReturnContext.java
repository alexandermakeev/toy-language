package org.example.toylanguage.context;

import org.example.toylanguage.expression.FunctionExpression;
import org.example.toylanguage.statement.ReturnStatement;
import org.example.toylanguage.statement.loop.AbstractLoopStatement;

/**
 * Associates a given {@link ReturnScope} with {@link org.example.toylanguage.statement.CompositeStatement}
 * <p>
 *
 * @see AbstractLoopStatement
 * @see ReturnStatement
 * @see FunctionExpression
 */
public class ReturnContext {
    private static ReturnScope scope = new ReturnScope();

    /**
     * Get current {@link ReturnScope}
     */
    public static ReturnScope getScope() {
        return scope;
    }

    /**
     * Reset state of the {@link ReturnContext} on block exit
     */
    public static void reset() {
        ReturnContext.scope = new ReturnScope();
    }
}
