package org.example.toylanguage.context.definition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Details for a function
 * <p>
 *
 * @see FunctionDefinition
 */
@RequiredArgsConstructor
@Getter
public class FunctionDetails {
    /**
     * Function's name
     */
    private final String name;
    /**
     * Names of the function arguments
     */
    private final List<String> arguments;

    /**
     * Compare function by its name and number of arguments
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionDetails that = (FunctionDetails) o;
        return Objects.equals(name, that.name) && arguments.size() == that.arguments.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments.size());
    }
}
