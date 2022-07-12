package org.example.toylanguage.context;

import lombok.Getter;

@Getter
public class NextScope {
    private boolean invoked;

    public void invoke() {
        setInvoked(true);
    }

    private void setInvoked(boolean invoked) {
        this.invoked = invoked;
    }
}
