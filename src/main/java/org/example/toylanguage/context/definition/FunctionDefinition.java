package org.example.toylanguage.context.definition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.statement.FunctionStatement;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class FunctionDefinition implements Definition {
    private final String name;
    private final List<String> arguments;
    private final FunctionStatement statement;
    private final DefinitionScope definitionScope;

    // function is compared by its name and number of arguments
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionDefinition that = (FunctionDefinition) o;
        return Objects.equals(name, that.name) && arguments.size() == that.arguments.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments.size());
    }
}
