package org.example.toylanguage;

import lombok.SneakyThrows;
import org.example.toylanguage.context.ExceptionContext;
import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.definition.DefinitionContext;
import org.example.toylanguage.statement.CompositeStatement;
import org.example.toylanguage.token.Token;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ToyLanguage {

    @SneakyThrows
    public void execute(Path path) {
        String sourceCode = Files.readString(path);
        List<Token> tokens = LexicalParser.parse(sourceCode);

        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        try {
            CompositeStatement statement = new CompositeStatement(null, path.getFileName().toString());
            StatementParser.parse(tokens, statement);
            statement.execute();
        } finally {
            DefinitionContext.endScope();
            MemoryContext.endScope();

            if (ExceptionContext.isRaised()) {
                ExceptionContext.printStackTrace();
            }
        }
    }

}
