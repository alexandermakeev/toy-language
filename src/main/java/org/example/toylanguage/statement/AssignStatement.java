package org.example.toylanguage.statement;

import org.example.toylanguage.context.VariableScope;
import org.example.toylanguage.expression.AssignExpression;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.operator.AssignmentOperator;

import static org.example.toylanguage.context.VariableScope.Global;

public class AssignStatement {
    public static <T extends AssignExpression & Expression> void execute(T key, Expression value, VariableScope variableScope) {
        AssignmentOperator assignmentOperator = new AssignmentOperator(key, value, variableScope);
        ExpressionStatement assignStatement = new ExpressionStatement(assignmentOperator);
        assignStatement.execute();
    }

    public static <T extends AssignExpression & Expression> void execute(T key, Expression value) {
        execute(key, value, Global);
    }
}
