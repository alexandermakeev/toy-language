package org.example.toylanguage.statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Statement {
    private final Integer rowNumber;
    private final String blockName;

    public abstract void execute();
}
