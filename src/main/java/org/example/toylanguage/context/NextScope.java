package org.example.toylanguage.context;

import lombok.Getter;

/**
 * Scope for the loop block defining if the <strong>next</strong> statement invoked
 * <p>
 *
 * @see NextContext
 */
@Getter
public class NextScope {
    private boolean invoked;

    /**
     * Notify the loop block about invoking the <strong>next</strong> statement
     */
    public void invoke() {
        this.invoked = true;
    }
}
