package org.example.toylanguage;

import org.example.toylanguage.context.MemoryContext;
import org.example.toylanguage.context.definition.DefinitionContext;
import org.example.toylanguage.expression.ClassExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.operator.*;
import org.example.toylanguage.expression.value.LogicalValue;
import org.example.toylanguage.expression.value.NumericValue;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.statement.*;
import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatementParserTest {

    @Test
    public void printTest() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("Hello World").build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "printTest");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(PrintStatement.class, statements.get(0).getClass());
        PrintStatement printStatement = (PrintStatement) statements.get(0);

        assertEquals(TextValue.class, printStatement.getExpression().getClass());
        TextValue textValue = (TextValue) printStatement.getExpression();

        assertEquals("Hello World", textValue.getValue());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

    @Test
    public void testInput() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Keyword).value("input").build(),
                Token.builder().type(TokenType.Variable).value("a").build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "testInput");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(InputStatement.class, statements.get(0).getClass());
        InputStatement inputStatement = (InputStatement) statements.get(0);

        assertEquals("a", inputStatement.getName());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

    @Test
    public void testAssignment() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value("=").build(),
                Token.builder().type(TokenType.Numeric).value("2").build(),
                Token.builder().type(TokenType.Operator).value("+").build(),
                Token.builder().type(TokenType.Numeric).value("5").build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "testAssignment");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(ExpressionStatement.class, statements.get(0).getClass());
        ExpressionStatement expressionStatement = (ExpressionStatement) statements.get(0);

        assertEquals(expressionStatement.getExpression().getClass(), AssignmentOperator.class);
        AssignmentOperator assignOperator = (AssignmentOperator) expressionStatement.getExpression();

        assertTrue(assignOperator.getLeft() instanceof VariableExpression);
        VariableExpression variableExpression = (VariableExpression) assignOperator.getLeft();
        assertEquals("a", variableExpression.getName());

        assertEquals(AdditionOperator.class, assignOperator.getRight().getClass());
        AdditionOperator operator = (AdditionOperator) assignOperator.getRight();

        assertEquals(NumericValue.class, operator.getLeft().getClass());
        NumericValue left = (NumericValue) operator.getLeft();
        assertEquals(2, left.getValue());

        assertEquals(NumericValue.class, operator.getRight().getClass());
        NumericValue right = (NumericValue) operator.getRight();
        assertEquals(5, right.getValue());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

    @Test
    public void testCondition() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Keyword).value("if").build(),
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value(">").build(),
                Token.builder().type(TokenType.Numeric).value("5").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("a is greater than 5").build(),
                Token.builder().type(TokenType.Keyword).value("elif").build(),
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value(">=").build(),
                Token.builder().type(TokenType.Numeric).value("1").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("a is greater than or equal to 1").build(),
                Token.builder().type(TokenType.Keyword).value("else").build(),
                Token.builder().type(TokenType.Keyword).value("print").build(),
                Token.builder().type(TokenType.Text).value("a is less than 1").build(),
                Token.builder().type(TokenType.Keyword).value("end").build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "testCondition");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(ConditionStatement.class, statements.get(0).getClass());
        ConditionStatement conditionStatement = (ConditionStatement) statements.get(0);

        Map<Expression, CompositeStatement> cases = conditionStatement.getCases();
        assertEquals(3, cases.size());

        List<Expression> conditions = new ArrayList<>(cases.keySet());

        //if case
        assertEquals(GreaterThanOperator.class, conditions.get(0).getClass());
        GreaterThanOperator ifCondition = (GreaterThanOperator) conditions.get(0);

        assertTrue(ifCondition.getLeft() instanceof VariableExpression);
        VariableExpression ifLeftExpression = (VariableExpression) ifCondition.getLeft();
        assertEquals("a", ifLeftExpression.getName());

        NumericValue ifRightExpression = (NumericValue) ifCondition.getRight();
        assertEquals(5, ifRightExpression.getValue());

        List<Statement> ifStatements = cases.get(ifCondition).getStatements2Execute();
        assertEquals(1, ifStatements.size());

        assertEquals(PrintStatement.class, ifStatements.get(0).getClass());
        PrintStatement ifPrintStatement = (PrintStatement) ifStatements.get(0);

        assertEquals(TextValue.class, ifPrintStatement.getExpression().getClass());
        TextValue ifPrintValue = (TextValue) ifPrintStatement.getExpression();
        assertEquals("a is greater than 5", ifPrintValue.getValue());

        //elif case
        assertEquals(GreaterThanOrEqualToOperator.class, conditions.get(1).getClass());
        GreaterThanOrEqualToOperator elifCondition = (GreaterThanOrEqualToOperator) conditions.get(1);

        assertTrue(elifCondition.getLeft() instanceof VariableExpression);
        VariableExpression elifLeftExpression = (VariableExpression) elifCondition.getLeft();
        assertEquals("a", elifLeftExpression.getName());

        NumericValue elifRightExpression = (NumericValue) elifCondition.getRight();
        assertEquals(1, elifRightExpression.getValue());

        List<Statement> elifStatements = cases.get(elifCondition).getStatements2Execute();
        assertEquals(1, elifStatements.size());

        assertEquals(PrintStatement.class, elifStatements.get(0).getClass());
        PrintStatement elifPrintStatement = (PrintStatement) elifStatements.get(0);

        assertEquals(TextValue.class, elifPrintStatement.getExpression().getClass());
        TextValue elifPrintValue = (TextValue) elifPrintStatement.getExpression();
        assertEquals("a is greater than or equal to 1", elifPrintValue.getValue());

        //else case
        assertEquals(LogicalValue.class, conditions.get(2).getClass());
        LogicalValue elseCondition = (LogicalValue) conditions.get(2);

        assertEquals(true, elseCondition.getValue());

        List<Statement> elseStatements = cases.get(elseCondition).getStatements2Execute();
        assertEquals(1, elseStatements.size());

        assertEquals(PrintStatement.class, elseStatements.get(0).getClass());
        PrintStatement elsePrintStatement = (PrintStatement) elseStatements.get(0);

        assertEquals(TextValue.class, elsePrintStatement.getExpression().getClass());
        TextValue elsePrintValue = (TextValue) elsePrintStatement.getExpression();
        assertEquals("a is less than 1", elsePrintValue.getValue());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

    @Test
    public void testClass() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Keyword).value("class").rowNumber(1).build(),
                Token.builder().type(TokenType.Variable).value("Person").rowNumber(1).build(),
                Token.builder().type(TokenType.GroupDivider).value("[").rowNumber(1).build(),
                Token.builder().type(TokenType.Variable).value("name").rowNumber(1).build(),
                Token.builder().type(TokenType.GroupDivider).value(",").rowNumber(1).build(),
                Token.builder().type(TokenType.Variable).value("age").rowNumber(1).build(),
                Token.builder().type(TokenType.GroupDivider).value("]").rowNumber(1).build(),
                Token.builder().type(TokenType.LineBreak).value("\n").rowNumber(1).build(),
                Token.builder().type(TokenType.Keyword).value("end").rowNumber(2).build(),
                Token.builder().type(TokenType.LineBreak).value("\n").rowNumber(2).build(),
                Token.builder().type(TokenType.Variable).value("person").rowNumber(3).build(),
                Token.builder().type(TokenType.Operator).value("=").rowNumber(3).build(),
                Token.builder().type(TokenType.Operator).value("new").rowNumber(3).build(),
                Token.builder().type(TokenType.Variable).value("Person").rowNumber(3).build(),
                Token.builder().type(TokenType.GroupDivider).value("[").rowNumber(3).build(),
                Token.builder().type(TokenType.Text).value("Randy Marsh").rowNumber(3).build(),
                Token.builder().type(TokenType.GroupDivider).value(",").rowNumber(3).build(),
                Token.builder().type(TokenType.Numeric).value("45").rowNumber(3).build(),
                Token.builder().type(TokenType.GroupDivider).value("]").rowNumber(3).build(),
                Token.builder().type(TokenType.LineBreak).value("\n").rowNumber(3).build(),
                Token.builder().type(TokenType.Keyword).value("print").rowNumber(4).build(),
                Token.builder().type(TokenType.Variable).value("person").rowNumber(4).build(),
                Token.builder().type(TokenType.Operator).value("::").rowNumber(4).build(),
                Token.builder().type(TokenType.Variable).value("name").rowNumber(4).build(),
                Token.builder().type(TokenType.Operator).value("+").rowNumber(4).build(),
                Token.builder().type(TokenType.Text).value(" is ").rowNumber(4).build(),
                Token.builder().type(TokenType.Operator).value("+").rowNumber(4).build(),
                Token.builder().type(TokenType.Variable).value("person").rowNumber(4).build(),
                Token.builder().type(TokenType.Operator).value("::").rowNumber(4).build(),
                Token.builder().type(TokenType.Variable).value("age").rowNumber(4).build(),
                Token.builder().type(TokenType.Operator).value("+").rowNumber(4).build(),
                Token.builder().type(TokenType.Text).value(" years old").rowNumber(4).build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "testClass");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(2, statements.size());

        // 1st statement
        assertEquals(ExpressionStatement.class, statements.get(0).getClass());
        ExpressionStatement expressionStatement = (ExpressionStatement) statements.get(0);

        assertEquals(expressionStatement.getExpression().getClass(), AssignmentOperator.class);
        AssignmentOperator assignStatement = (AssignmentOperator) expressionStatement.getExpression();

        assertTrue(assignStatement.getLeft() instanceof VariableExpression);
        VariableExpression variableExpression = (VariableExpression) assignStatement.getLeft();
        assertEquals("person", variableExpression.getName());

        assertEquals(ClassInstanceOperator.class, assignStatement.getRight().getClass());
        ClassInstanceOperator instanceOperator = (ClassInstanceOperator) assignStatement.getRight();

        assertEquals(ClassExpression.class, instanceOperator.getValue().getClass());
        ClassExpression type = (ClassExpression) instanceOperator.getValue();

        assertEquals("Person", type.getName());
        assertEquals("Randy Marsh", type.getPropertiesExpressions().get(0).toString());
        assertEquals("45", type.getPropertiesExpressions().get(1).toString());

        // 2nd statement
        PrintStatement printStatement = (PrintStatement) statements.get(1);
        assertEquals(AdditionOperator.class, printStatement.getExpression().getClass());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

    @Test
    public void testComment() {
        List<Token> tokens = List.of(
                Token.builder().type(TokenType.Comment).value("# a = 5").build(),
                Token.builder().type(TokenType.LineBreak).value("\n").build(),
                Token.builder().type(TokenType.Variable).value("a").build(),
                Token.builder().type(TokenType.Operator).value("=").build(),
                Token.builder().type(TokenType.Numeric).value("5").build(),
                Token.builder().type(TokenType.Comment).value("# a is equal to 5").build()
        );
        DefinitionContext.pushScope(DefinitionContext.newScope());
        MemoryContext.pushScope(MemoryContext.newScope());
        CompositeStatement statement = new CompositeStatement(null, "testComment");
        StatementParser.parse(tokens, statement);

        List<Statement> statements = statement.getStatements2Execute();
        assertEquals(1, statements.size());

        assertEquals(ExpressionStatement.class, statements.get(0).getClass());
        ExpressionStatement expressionStatement = (ExpressionStatement) statements.get(0);

        assertEquals(expressionStatement.getExpression().getClass(), AssignmentOperator.class);
        AssignmentOperator assignStatement = (AssignmentOperator) expressionStatement.getExpression();

        assertTrue(assignStatement.getLeft() instanceof VariableExpression);
        VariableExpression variableExpression = (VariableExpression) assignStatement.getLeft();
        assertEquals("a", variableExpression.getName());
        assertEquals(NumericValue.class, assignStatement.getRight().getClass());
        NumericValue numericValue = (NumericValue) assignStatement.getRight();

        assertEquals(5, numericValue.getValue());

        DefinitionContext.endScope();
        MemoryContext.endScope();
    }

}