package org.example.toylanguage;

import lombok.SneakyThrows;
import org.example.toylanguage.definition.FunctionDefinition;
import org.example.toylanguage.definition.StructureDefinition;
import org.example.toylanguage.exception.SyntaxException;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.FunctionExpression;
import org.example.toylanguage.expression.StructureExpression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.*;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.statement.*;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;
import org.example.toylanguage.token.TokensStack;

import java.util.*;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;

public class StatementParser {
    private final TokensStack tokens;
    private final Map<String, StructureDefinition> structures;
    private final Map<String, FunctionDefinition> functions;
    private final Scanner scanner;

    public StatementParser(List<Token> tokens) {
        this.tokens = new TokensStack(tokens);
        this.structures = new HashMap<>();
        this.functions = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public Statement parse() {
        CompositeStatement root = new CompositeStatement();
        while (hasNextStatement()) {
            Statement statement = parseExpression();
            root.addStatement(statement);
        }
        return root;
    }

    private boolean hasNextStatement() {
        if (!tokens.hasNext())
            return false;
        if (tokens.peek(TokenType.Operator, TokenType.Variable))
            return true;
        if (tokens.peek(TokenType.Keyword)) {
            return !tokens.peek(TokenType.Keyword, "end");
        }
        return false;
    }

    private Statement parseExpression() {
        Token token = tokens.next(TokenType.Keyword, TokenType.Variable, TokenType.Operator);
        switch (token.getType()) {
            case Variable:
            case Operator:
                tokens.back(); // go back to read an expression from the beginning
                Expression value = new ExpressionReader().readExpression();
                return new ExpressionStatement(value);
            case Keyword:
                switch (token.getValue()) {
                    case "print": {
                        Expression expression = new ExpressionReader().readExpression();
                        return new PrintStatement(expression);
                    }
                    case "input": {
                        Token variable = tokens.next(TokenType.Variable);
                        return new InputStatement(variable.getValue(), scanner::nextLine);
                    }
                    case "if": {
                        tokens.back();
                        ConditionStatement conditionStatement = new ConditionStatement();

                        while (!tokens.peek(TokenType.Keyword, "end")) {
                            //read condition case
                            Token type = tokens.next(TokenType.Keyword, "if", "elif", "else");
                            Expression caseCondition;
                            if (type.getValue().equals("else")) {
                                caseCondition = new LogicalValue(true); //else case does not have the condition
                            } else {
                                caseCondition = new ExpressionReader().readExpression();
                            }
                            tokens.next(TokenType.Keyword, "then"); //skip then

                            //read case statements
                            CompositeStatement caseStatement = new CompositeStatement();
                            while (!tokens.peek(TokenType.Keyword, "elif", "else", "end")) {
                                Statement statement = parseExpression();
                                caseStatement.addStatement(statement);
                            }

                            //add case
                            conditionStatement.addCase(caseCondition, caseStatement);
                        }
                        tokens.next(TokenType.Keyword, "end");

                        return conditionStatement;
                    }
                    case "struct": {
                        Token type = tokens.next(TokenType.Variable);

                        List<String> args = new ArrayList<>();

                        if (tokens.peek(TokenType.GroupDivider, "[")) {

                            tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

                            while (!tokens.peek(TokenType.GroupDivider, "]")) {
                                Token arg = tokens.next(TokenType.Variable);
                                args.add(arg.getValue());

                                if (tokens.peek(TokenType.GroupDivider, ","))
                                    tokens.next();
                            }

                            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
                        }

                        structures.put(type.getValue(), new StructureDefinition(type.getValue(), new ArrayList<>(args)));

                        return null;
                    }
                    case "fun": {
                        Token type = tokens.next(TokenType.Variable);

                        List<String> args = new ArrayList<>();

                        if (tokens.peek(TokenType.GroupDivider, "[")) {

                            tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

                            while (!tokens.peek(TokenType.GroupDivider, "]")) {
                                Token arg = tokens.next(TokenType.Variable);
                                args.add(arg.getValue());

                                if (tokens.peek(TokenType.GroupDivider, ","))
                                    tokens.next();
                            }

                            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
                        }

                        FunctionStatement functionStatement = new FunctionStatement();
                        FunctionDefinition functionDefinition = new FunctionDefinition(type.getValue(), args, functionStatement);
                        functions.put(type.getValue(), functionDefinition);

                        while (!tokens.peek(TokenType.Keyword, "end")) {
                            Statement statement = parseExpression();
                            functionStatement.addStatement(statement);
                        }
                        tokens.next(TokenType.Keyword, "end");

                        return null;
                    }
                    case "return": {
                        Expression expression = new ExpressionReader().readExpression();
                        return new ReturnStatement(expression);
                    }
                }
            default:
                throw new SyntaxException(String.format("Statement can't start with the following lexeme `%s`", token));
        }
    }

    private class ExpressionReader {
        private final Stack<Expression> operands;
        private final Stack<Operator> operators;

        private ExpressionReader() {
            this.operands = new Stack<>();
            this.operators = new Stack<>();
        }

        private Expression readExpression() {
            while (tokens.peekSameLine(TokenType.Operator, TokenType.Variable, TokenType.Numeric, TokenType.Logical, TokenType.Null, TokenType.Text)) {
                Token token = tokens.next();
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
                            case Null:
                                operand = NULL_INSTANCE;
                                break;
                            case Variable:
                            default:
                                if (!operators.isEmpty() && operators.peek() == Operator.StructureInstance) {
                                    operand = readStructureInstance(token);
                                } else if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {
                                    operand = readFunctionInvocation(token);
                                } else {
                                    operand = new VariableExpression(value);
                                }
                        }
                        operands.push(operand);
                }
            }

            while (!operators.isEmpty()) {
                applyTopOperator();
            }

            if (operands.isEmpty()) {
                return NULL_INSTANCE;
            } else {
                return operands.pop();
            }
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

        private StructureExpression readStructureInstance(Token token) {
            StructureDefinition definition = structures.get(token.getValue());
            if (definition == null) {
                throw new SyntaxException(String.format("Structure is not defined: %s", token.getValue()));
            }

            List<Expression> arguments = new ArrayList<>();
            if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {

                tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

                while (!tokens.peekSameLine(TokenType.GroupDivider, "]")) {
                    Expression value = new ExpressionReader().readExpression();
                    arguments.add(value);

                    if (tokens.peekSameLine(TokenType.GroupDivider, ","))
                        tokens.next();
                }

                tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
            }
            return new StructureExpression(definition, arguments);
        }

        private FunctionExpression readFunctionInvocation(Token token) {
            FunctionDefinition definition = functions.get(token.getValue());
            if (definition == null) {
                throw new SyntaxException(String.format("Function is not defined: %s", token.getValue()));
            }

            List<Expression> arguments = new ArrayList<>();
            if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {

                tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

                while (!tokens.peekSameLine(TokenType.GroupDivider, "]")) {
                    Expression value = new ExpressionReader().readExpression();
                    arguments.add(value);

                    if (tokens.peekSameLine(TokenType.GroupDivider, ","))
                        tokens.next();
                }

                tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
            }

            return new FunctionExpression(definition, arguments);
        }
    }
}
