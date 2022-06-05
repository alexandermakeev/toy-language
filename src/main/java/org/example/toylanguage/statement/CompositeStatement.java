package org.example.toylanguage.statement;

import lombok.Getter;
import org.example.toylanguage.context.ReturnContext;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CompositeStatement implements Statement {
    private final List<Statement> statements2Execute = new ArrayList<>();

    public void addStatement(Statement statement) {
        if (statement != null)
            statements2Execute.add(statement);
    }

    @Override
    public void execute() {
        for (Statement statement : statements2Execute) {
            statement.execute();

            //stop the execution in case ReturnStatement has been invoked
            if (ReturnContext.getScope().isInvoked())
                return;
        }
    }
}
