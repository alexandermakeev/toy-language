package org.example.toylanguage.expression;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.toylanguage.exception.SyntaxException;
import org.example.toylanguage.expression.operator.*;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;
import org.example.toylanguage.token.TokensStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static org.example.toylanguage.expression.value.NullValue.NULL_INSTANCE;
import static org.example.toylanguage.expression.value.ThisValue.THIS_INSTANCE;

public class ExpressionReader {
    private final Stack<Expression> operands;
    private final Stack<Operator> operators;
    @Getter
    private final TokensStack tokens;

    private ExpressionReader(TokensStack tokens) {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
        this.tokens = tokens;
    }

    public static Expression readExpression(TokensStack tokens) {
        ExpressionReader expressionReader = new ExpressionReader(tokens);
        return expressionReader.readExpression();
    }

    public static Expression readExpression(ExpressionReader expressionReader) {
        return readExpression(expressionReader.getTokens());
    }

    private boolean hasNextToken() {
        if (tokens.peekSameLine(TokenType.Operator, TokenType.Variable, TokenType.Numeric, TokenType.Logical,
                TokenType.Null, TokenType.This, TokenType.Text))
            return true;
        //beginning of an array
        if (tokens.peekSameLine(TokenType.GroupDivider, "{"))
            return true;
        return false;
    }

    private Expression readExpression() {
        while (hasNextToken()) {
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
                        case GroupDivider:
                            if (Objects.equals(token.getValue(), "{")) {
                                operand = readArrayInstance();
                                break;
                            }
                        case Null:
                            operand = NULL_INSTANCE;
                            break;
                        case This:
                            operand = THIS_INSTANCE;
                            break;
                        case Variable:
                        default:
                            if (!operators.isEmpty() && operators.peek() == Operator.ClassInstance) {
                                operand = readClassInstance(token);
                            } else if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {
                                operand = readFunctionInvocation(token);
                            } else if (tokens.peekSameLine(TokenType.GroupDivider, "{")) {
                                operand = readArrayValue(token);
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

    // read class instance: new Class[arguments]
    private ClassExpression readClassInstance(Token token) {
        List<Expression> arguments = new ArrayList<>();
        if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {

            tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

            while (!tokens.peekSameLine(TokenType.GroupDivider, "]")) {
                Expression value = ExpressionReader.readExpression(this);
                arguments.add(value);

                if (tokens.peekSameLine(TokenType.GroupDivider, ","))
                    tokens.next();
            }

            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
        }
        return new ClassExpression(token.getValue(), arguments);
    }

    // read function invocation: function_call[arguments]
    private FunctionExpression readFunctionInvocation(Token token) {
        List<Expression> arguments = new ArrayList<>();
        if (tokens.peekSameLine(TokenType.GroupDivider, "[")) {

            tokens.next(TokenType.GroupDivider, "["); //skip open square bracket

            while (!tokens.peekSameLine(TokenType.GroupDivider, "]")) {
                Expression value = ExpressionReader.readExpression(this);
                arguments.add(value);

                if (tokens.peekSameLine(TokenType.GroupDivider, ","))
                    tokens.next();
            }

            tokens.next(TokenType.GroupDivider, "]"); //skip close square bracket
        }

        return new FunctionExpression(token.getValue(), arguments);
    }

    // read array instantiation: array = {1,2,3}
    private ArrayExpression readArrayInstance() {
        List<Expression> values = new ArrayList<>();

        while (!tokens.peekSameLine(TokenType.GroupDivider, "}")) {
            Expression value = ExpressionReader.readExpression(this);
            values.add(value);

            if (tokens.peekSameLine(TokenType.GroupDivider, ","))
                tokens.next();
        }

        tokens.next(TokenType.GroupDivider, "}"); //skip close square bracket

        return new ArrayExpression(values);
    }

    // read array value: array{index}
    private ArrayValueOperator readArrayValue(Token token) {
        VariableExpression array = new VariableExpression(token.getValue());
        tokens.next(TokenType.GroupDivider, "{");
        Expression arrayIndex = ExpressionReader.readExpression(this);
        tokens.next(TokenType.GroupDivider, "}");

        return new ArrayValueOperator(array, arrayIndex);
    }
}

