package org.example.toylanguage;

import org.example.toylanguage.expression.StructureExpression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.AdditionOperator;
import org.example.toylanguage.expression.operator.GreaterThanOperator;
import org.example.toylanguage.expression.operator.StructureValueOperator;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.*;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementParserTest {

    @Test
    public void printTest() {
        List<Token> tokens = Arrays.asList(
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("Hello World").build()
        );
        StatementParser parser = new StatementParser(tokens);
        CompositeStatement statement = (CompositeStatement) parser.parse();

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(PrintStatement.class, statements.get(0).getClass());
        PrintStatement printStatement = (PrintStatement) statements.get(0);

        assertEquals(TextValue.class, printStatement.getExpression().getClass());
        TextValue textValue = (TextValue) printStatement.getExpression();

        assertEquals("Hello World", textValue.getValue());
    }

    @Test
    public void testInput() {

        List<Token> tokens = Arrays.asList(
                Token.builder().type(TokenType.Keyword).value("input").build(),
                Token.builder().type(TokenType.Variable).value("a").build()
        );
        StatementParser parser = new StatementParser(tokens);
        CompositeStatement statement = (CompositeStatement) parser.parse();

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(InputStatement.class, statements.get(0).getClass());
        InputStatement inputStatement = (InputStatement) statements.get(0);

        assertEquals("a", inputStatement.getName());
    }

    @Test
    public void testAssignment() {

        List<Token> tokens = Arrays.asList(
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value("=").build(),
                Token.builder().type(TokenType.Numeric).value("2").build(),
                Token.builder().type(TokenType.Operator).value("+").build(),
                Token.builder().type(TokenType.Numeric).value("5").build()
        );
        StatementParser parser = new StatementParser(tokens);
        CompositeStatement statement = (CompositeStatement) parser.parse();

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(AssignStatement.class, statements.get(0).getClass());
        AssignStatement assignStatement = (AssignStatement) statements.get(0);
        assertEquals("a", assignStatement.getName());

        assertEquals(AdditionOperator.class, assignStatement.getExpression().getClass());
        AdditionOperator operator = (AdditionOperator) assignStatement.getExpression();

        assertEquals(NumericValue.class, operator.getLeft().getClass());
        NumericValue left = (NumericValue) operator.getLeft();
        assertEquals(2, left.getValue());

        assertEquals(NumericValue.class, operator.getRight().getClass());
        NumericValue right = (NumericValue) operator.getRight();
        assertEquals(5, right.getValue());
    }

    @Test
    public void testCondition() {

        List<Token> tokens = Arrays.asList(
                Token.builder().type(TokenType.Keyword).value("if").build(),
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value(">").build(),
                Token.builder().type(TokenType.Numeric).value("1").build(),
                Token.builder().type(TokenType.Keyword).value("then").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("a is greater than 1").build(),
                Token.builder().type(TokenType.Keyword).value("end").build()
        );
        StatementParser parser = new StatementParser(tokens);
        CompositeStatement statement = (CompositeStatement) parser.parse();

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(ConditionStatement.class, statements.get(0).getClass());
        ConditionStatement conditionStatement = (ConditionStatement) statements.get(0);

        assertEquals(GreaterThanOperator.class, conditionStatement.getCondition().getClass());
        GreaterThanOperator condition = (GreaterThanOperator) conditionStatement.getCondition();

        assertEquals(VariableExpression.class, condition.getLeft().getClass());
        VariableExpression left = (VariableExpression) condition.getLeft();
        assertEquals("a", left.getName());

        NumericValue right = (NumericValue) condition.getRight();
        assertEquals(1, right.getValue());

        List<Statement> conditionStatements = conditionStatement.getStatements2Execute();
        assertEquals(1, conditionStatements.size());

        assertEquals(PrintStatement.class, conditionStatements.get(0).getClass());
        PrintStatement printStatement = (PrintStatement) conditionStatements.get(0);

        assertEquals(TextValue.class, printStatement.getExpression().getClass());
        TextValue printValue = (TextValue) printStatement.getExpression();
        assertEquals("a is greater than 1", printValue.getValue());
    }

    @Test
    public void testObject() {

        List<Token> tokens = Arrays.asList(
                Token.builder().type(TokenType.Keyword).value("struct").build(),
                Token.builder().type(TokenType.Variable).value("Person").build(),
                Token.builder().type(TokenType.Keyword).value("arg").build(),
                Token.builder().type(TokenType.Variable).value("name").build(),
                Token.builder().type(TokenType.Keyword).value("arg").build(),
                Token.builder().type(TokenType.Variable).value("age").build(),
                Token.builder().type(TokenType.Keyword).value("end").build(),
                Token.builder().type(TokenType.Variable).value("person").build(),
                Token.builder().type(TokenType.Operator).value("=").build(),
                Token.builder().type(TokenType.Keyword).value("new").build(),
                Token.builder().type(TokenType.Variable).value("Person").build(),
                Token.builder().type(TokenType.GroupDivider).value("[").build(),
                Token.builder().type(TokenType.Text).value("John").build(),
                Token.builder().type(TokenType.Numeric).value("25").build(),
                Token.builder().type(TokenType.GroupDivider).value("]").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Variable).value("person").build(),
                Token.builder().type(TokenType.Operator).value("::").build(),
                Token.builder().type(TokenType.Variable).value("name").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Variable).value("person").build(),
                Token.builder().type(TokenType.Operator).value("::").build(),
                Token.builder().type(TokenType.Variable).value("age").build()
        );
        StatementParser parser = new StatementParser(tokens);
        CompositeStatement statement = (CompositeStatement) parser.parse();

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(3, statements.size());

        // 1st statement
        assertEquals(AssignStatement.class, statements.get(0).getClass());
        AssignStatement assignStatement = (AssignStatement) statements.get(0);
        assertEquals("person", assignStatement.getName());

        assertEquals(StructureExpression.class, assignStatement.getExpression().getClass());
        StructureExpression expression = (StructureExpression) assignStatement.getExpression();

        Value<?> name = expression.getArgumentValue("name");
        assertEquals(TextValue.class, name.getClass());
        assertEquals("John", ((TextValue) name).getValue());

        Value<?> age = expression.getArgumentValue("age");
        assertEquals(NumericValue.class, age.getClass());
        assertEquals(25, ((NumericValue) age).getValue());

        // 2nd statement
        assertEquals(PrintStatement.class, statements.get(1).getClass());

        PrintStatement namePrintStatement = (PrintStatement) statements.get(1);
        assertEquals(StructureValueOperator.class, namePrintStatement.getExpression().getClass());

        StructureValueOperator nameOperator = (StructureValueOperator) namePrintStatement.getExpression();

        assertEquals(VariableExpression.class, nameOperator.getLeft().getClass());
        VariableExpression nameExpression = (VariableExpression) nameOperator.getLeft();
        assertEquals("person", nameExpression.getName());

        assertEquals(VariableExpression.class, nameOperator.getRight().getClass());
        VariableExpression nameVariable = (VariableExpression) nameOperator.getRight();
        assertEquals("name", nameVariable.getName());

        // 3rd statement
        assertEquals(PrintStatement.class, statements.get(2).getClass());

        PrintStatement agePrintStatement = (PrintStatement) statements.get(2);
        assertEquals(StructureValueOperator.class, agePrintStatement.getExpression().getClass());

        StructureValueOperator ageOperator = (StructureValueOperator) agePrintStatement.getExpression();

        assertEquals(VariableExpression.class, ageOperator.getLeft().getClass());
        VariableExpression ageExpression = (VariableExpression) ageOperator.getLeft();
        assertEquals("person", ageExpression.getName());

        assertEquals(VariableExpression.class, ageOperator.getRight().getClass());
        VariableExpression ageVariable = (VariableExpression) ageOperator.getRight();
        assertEquals("age", ageVariable.getName());
    }

}