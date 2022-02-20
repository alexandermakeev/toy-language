package org.example.toylanguage;

import lombok.SneakyThrows;
import org.example.toylanguage.statement.Statement;
import org.example.toylanguage.token.Token;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ToyLanguage {

    @SneakyThrows
    public void execute(Path path) {
        String source = Files.readString(path);
        LexicalParser lexicalParser = new LexicalParser(source);
        List<Token> tokens = lexicalParser.parse();
        StatementParser statementParser = new StatementParser(tokens);
        Statement statement = statementParser.parse();
        statement.execute();
    }

}
