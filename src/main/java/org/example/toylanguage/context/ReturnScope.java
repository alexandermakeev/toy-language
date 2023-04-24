package org.example.toylanguage.context;

import lombok.Getter;
import org.example.toylanguage.expression.value.Value;

/**
 * Scope for the {@link org.example.toylanguage.statement.CompositeStatement} defining if the <strong>return</strong> statement invoked
 * <p>
 *
 * @see BreakContext
 */
@Getter
public class ReturnScope {
    private boolean invoked;
    private Value<?> result;

    /**
     * Notify current scope that <strong>return</strong> statement invoked
     */
    public void invoke(Value<?> result) {
        this.invoked = true;
        this.result = result;
    }
}
