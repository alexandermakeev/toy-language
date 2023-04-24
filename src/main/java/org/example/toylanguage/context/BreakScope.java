package org.example.toylanguage.context;

import lombok.Getter;
import org.example.toylanguage.statement.loop.BreakStatement;

/**
 * Scope for the loop block defining if the <strong>break</strong> statement invoked
 * <p>
 *
 * @see BreakContext
 * @see BreakStatement
 */
@Getter
public class BreakScope {
    private boolean invoked;

    /**
     * Notify the loop block about invoking the <strong>break</strong> statement
     */
    public void invoke() {
        this.invoked = true;
    }
}
