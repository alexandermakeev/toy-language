package org.example.toylanguage;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.exception.SyntaxException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.StructureExpression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.BinaryOperatorExpression;
import org.example.toylanguage.expression.operator.Operator;
import org.example.toylanguage.expression.operator.OperatorExpression;
import org.example.toylanguage.expression.operator.UnaryOperatorExpression;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.*;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;

import java.util.*;
import java.util.stream.Stream;

public class StatementParser {
    private final List<Token> tokens;
    private final Map<String, Value<?>> variables;
    private final Map<String, StructureDefinition> structures;
    private final Scanner scanner;
    private int position;

    public StatementParser(List<Token> tokens) {
        this.tokens = tokens;
        this.variables = new HashMap<>();
        this.structures = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public Statement parse() {
        CompositeStatement root = new CompositeStatement();
        while (position < tokens.size()) {
            Statement statement = parseExpression();
            root.addStatement(statement);
        }
        return root;
    }

    private Statement parseExpression() {
        Token token = next(TokenType.Keyword, TokenType.Variable);
        switch (token.getType()) {
            case Variable:
                next(TokenType.Operator, "="); //skip equals

                Expression value;
                if (peek(TokenType.Keyword, "new")) {
                    value = readInstance();
                } else {
                    value = readExpression();
                }

                return new AssignStatement(token.getValue(), value, variables::put);
            case Keyword:
                switch (token.getValue()) {
                    case "print":
                        Expression expression = readExpression();
                        return new PrintStatement(expression);
                    case "input":
                        Token variable = next(TokenType.Variable);
                        return new InputStatement(variable.getValue(), scanner::nextLine, variables::put);
                    case "if":
                        Expression condition = readExpression();
                        next(TokenType.Keyword, "then"); //skip then

                        ConditionStatement conditionStatement = new ConditionStatement(condition);
                        while (!peek(TokenType.Keyword, "end")) {
                            Statement statement = parseExpression();
                            conditionStatement.addStatement(statement);
                        }
                        next(TokenType.Keyword, "end"); //skip end

                        return conditionStatement;
                    case "struct":
                        Token type = next(TokenType.Variable);

                        Set<String> args = new HashSet<>();
                        while (!peek(TokenType.Keyword, "end")) {
                            next(TokenType.Keyword, "arg");

                            Token arg = next(TokenType.Variable);
                            args.add(arg.getValue());
                        }
                        next(TokenType.Keyword, "end"); //skip end

                        structures.put(type.getValue(), new StructureDefinition(type.getValue(), new ArrayList<>(args)));

                        return null;
                }
            default:
                throw new SyntaxException(String.format("Statement can't start with the following lexeme `%s`", token));
        }
    }

    private Expression readInstance() {
        next(TokenType.Keyword, "new"); //skip new

        Token type = next(TokenType.Variable);

        List<Expression> arguments = new ArrayList<>();

        if (peek(TokenType.GroupDivider, "[")) {

            next(TokenType.GroupDivider, "["); //skip open square bracket

            while (!peek(TokenType.GroupDivider, "]")) {
                Expression value = readExpression();
                arguments.add(value);
            }

            next(TokenType.GroupDivider, "]"); //skip close square bracket
        }

        StructureDefinition definition = structures.get(type.getValue());
        if (definition == null) {
            throw new SyntaxException(String.format("Structure is not defined: %s", type.getValue()));
        }
        return new StructureExpression(definition, arguments, variables::get);
    }


    @SneakyThrows
    private Expression readExpression() {
        Expression left = nextExpression();

        //recursively read an expression
        while (peek(TokenType.Operator)) {
            Token operation = next(TokenType.Operator);
            Class<? extends OperatorExpression> operatorType = Operator.getType(operation.getValue());
            if (BinaryOperatorExpression.class.isAssignableFrom(operatorType)) {
                Expression right = nextExpression();
                left = operatorType
                        .getConstructor(Expression.class, Expression.class)
                        .newInstance(left, right);
            } else if (UnaryOperatorExpression.class.isAssignableFrom(operatorType)) {
                left = operatorType
                        .getConstructor(Expression.class)
                        .newInstance(left);
            }
        }

        return left;
    }

    private Expression nextExpression() {
        Token token = next(TokenType.Variable, TokenType.Numeric, TokenType.Logical, TokenType.Text);
        String value = token.getValue();
        switch (token.getType()) {
            case Numeric:
                return new NumericValue(Integer.parseInt(value));
            case Logical:
                return new LogicalValue(Boolean.valueOf(value));
            case Text:
                return new TextValue(value);
            case Variable:
            default:
                return new VariableExpression(value, variables::get);
        }
    }

    private Token next(TokenType type, TokenType... types) {
        TokenType[] tokenTypes = ArrayUtils.add(types, type);
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            if (Stream.of(tokenTypes).anyMatch(t -> t == token.getType())) {
                position++;
                return token;
            }
        }
        Token previousToken = tokens.get(position - 1);
        throw new SyntaxException(String.format("After `%s` declaration expected any of the following lexemes `%s`", previousToken, Arrays.toString(tokenTypes)));
    }

    private Token next(TokenType type, String value) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            if (token.getType() == type && token.getValue().equals(value)) {
                position++;
                return token;
            }
        }
        Token previousToken = tokens.get(position - 1);
        throw new SyntaxException(String.format("After `%s` declaration expected `%s, %s` lexeme", previousToken, type, value));
    }

    private boolean peek(TokenType type, String content) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return type == token.getType() && token.getValue().equals(content);
        }
        return false;
    }

    private boolean peek(TokenType type) {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return token.getType() == type;
        }
        return false;
    }
}
