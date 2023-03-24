package org.example.toylanguage.expression.operator;

import org.example.toylanguage.context.definition.ClassDetails;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.VariableExpression;
import org.example.toylanguage.expression.value.ClassValue;
import org.example.toylanguage.expression.value.Value;

/**
 * Cast a class instance from one type to other
 */
public class ClassCastOperator extends BinaryOperatorExpression {
    public ClassCastOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> evaluate() {
        // evaluate expressions
        ClassValue classInstance = (ClassValue) getLeft().evaluate();
        String typeToCastName = ((VariableExpression) getRight()).getName();

        // retrieve class details
        ClassDetails classDetails = classInstance.getValue().getClassDetails();

        // check if the type to cast is different from original
        if (classDetails.getName().equals(typeToCastName)) {
            return classInstance;
        } else {
            // retrieve ClassValue of other type
            return classInstance.getRelation(typeToCastName);
        }
    }
}
