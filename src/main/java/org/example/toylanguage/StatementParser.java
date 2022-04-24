package org.example.toylanguage;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.exception.SyntaxException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.StructureExpression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.*;
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
        while (hasNext()) {
            Statement statement = parseExpression();
            root.addStatement(statement);
        }
        return root;
    }

    private Statement parseExpression() {
        Token token = next(TokenType.Keyword, TokenType.Variable, TokenType.Operator);
        switch (token.getType()) {
            case Variable:
            case Operator:
                position--;
                Expression value = new ExpressionReader().readExpression();

                if (value instanceof AssignmentOperator) {
                    VariableExpression variable = (VariableExpression) ((AssignmentOperator) value).getLeft();
                    return new AssignStatement(variable.getName(), ((AssignmentOperator) value).getRight(), variables::put);
                } else {
                    throw new SyntaxException(String.format("Unsupported statement: `%s`", value));
                }
            case Keyword:
                switch (token.getValue()) {
                    case "print":
                        Expression expression = new ExpressionReader().readExpression();
                        return new PrintStatement(expression);
                    case "input":
                        Token variable = next(TokenType.Variable);
                        return new InputStatement(variable.getValue(), scanner::nextLine, variables::put);
                    case "if":
                        Expression condition = new ExpressionReader().readExpression();
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

                        List<String> args = new ArrayList<>();
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

    private Token next(TokenType type, TokenType... types) {
        skipLineBreaks();
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

    private boolean hasNext() {
		skipLineBreaks();
		return position < tokens.size();
    }

    private Token next(TokenType type, String value) {
        skipLineBreaks();
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

    private Token next() {
        skipLineBreaks();
        return tokens.get(position++);
    }

    private boolean peek(TokenType type, String content) {
        skipLineBreaks();
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            return type == token.getType() && token.getValue().equals(content);
        }
        return false;
    }

    private void skipLineBreaks() {
        while (position != tokens.size() && tokens.get(position).getType() == TokenType.LineBreak)
			position++;
    }

    private class ExpressionReader {
        private final Stack<Expression> operands;
        private final Stack<Operator> operators;

        private ExpressionReader() {
            this.operands = new Stack<>();
            this.operators = new Stack<>();
        }

        private Expression readExpression() {
            while (peek(TokenType.Operator, TokenType.Variable, TokenType.Numeric, TokenType.Logical, TokenType.Text)) {
                Token token = next();
                switch (token.getType()) {
                    case Operator:
                        Operator operator = Operator.getType(token.getValue());
                        switch (operator) {
                            case LeftParen:
                                operators.push(operator);
                                break;
                            case RightParen:
                                //until left bracket is not reached
                                while (!operators.empty() && operators.peek() != Operator.LeftParen)
                                    applyTopOperator();
                                operators.pop(); //pop left bracket
                                break;
                            default:
                                //until top operator has greater precedence
                                while (!operators.isEmpty() && operators.peek().greaterThan(operator))
                                    applyTopOperator();
                                operators.push(operator); // finally, add less prioritized operator
                        }
                        break;
                    default:
                        String value = token.getValue();
                        Expression operand;
                        switch (token.getType()) {
                            case Numeric:
                                operand = new NumericValue(Double.parseDouble(value));
                                break;
                            case Logical:
                                operand = new LogicalValue(Boolean.valueOf(value));
                                break;
                            case Text:
                                operand = new TextValue(value);
                                break;
                            case Variable:
                            default:
                                if (!operators.isEmpty() && operators.peek() == Operator.StructureInstance) {
                                    operand = readInstance(token);
                                } else {
                                    operand = new VariableExpression(value, variables::get, variables::put);
                                }
                        }
                        operands.push(operand);
                }
            }

            while (!operators.isEmpty()) {
                applyTopOperator();
            }

            return operands.pop();
        }

        @SneakyThrows
        private void applyTopOperator() {
            // e.g. a + b
            Operator operator = operators.pop();
            Class<? extends OperatorExpression> operatorType = operator.getType();
            Expression left = operands.pop();
            if (BinaryOperatorExpression.class.isAssignableFrom(operatorType)) {
                Expression right = operands.pop();
                operands.push(operatorType
                        .getConstructor(Expression.class, Expression.class)
                        .newInstance(right, left));
            } else if (UnaryOperatorExpression.class.isAssignableFrom(operatorType)) {
                // e.g. new Instance []
                operands.push(operatorType
                        .getConstructor(Expression.class)
                        .newInstance(left));
            } else {
                throw new SyntaxException(String.format("Operator `%s` is not supported", operatorType));
            }
        }

        private Expression readInstance(Token token) {
            StructureDefinition definition = structures.get(token.getValue());

            List<Expression> arguments = new ArrayList<>();
            if (StatementParser.this.peek(TokenType.GroupDivider, "[")) {

                next(TokenType.GroupDivider, "["); //skip open square bracket

                while (!StatementParser.this.peek(TokenType.GroupDivider, "]")) {
                    Expression value = new ExpressionReader().readExpression();
                    arguments.add(value);

                    if (StatementParser.this.peek(TokenType.GroupDivider, ","))
                        next();
                }

                next(TokenType.GroupDivider, "]"); //skip close square bracket
            }

            if (definition == null) {
                throw new SyntaxException(String.format("Structure is not defined: %s", token.getValue()));
            }
            return new StructureExpression(definition, arguments, variables::get);
        }

        private boolean peek(TokenType type, TokenType... types) {
            TokenType[] tokenTypes = ArrayUtils.add(types, type);
            if (position < tokens.size()) {
                Token token = tokens.get(position);
                return Stream.of(tokenTypes).anyMatch(t -> t == token.getType());
            }
            return false;
        }
    }
}
