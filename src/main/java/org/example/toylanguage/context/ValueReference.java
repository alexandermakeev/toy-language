package org.example.toylanguage.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.toylanguage.expression.Expression;
import org.example.toylanguage.expression.value.Value;

/**
 * Wrapper for the Value to keep the properties relation between a Base class and a Derived class
 *
 * <pre>{@code
 * # Declare the Base class A
 * class A [a_value]
 * end
 *
 * # Declare the Derived class B that inherits class A and initializes its `a_value` property with the `b_value` parameter
 * class B [b_value]: A [b_value]
 * end
 *
 * # Create an instance of class B
 * b = new B [ b_value ]
 *
 * # If we change the `b_value` property, the A class's property `a_value` should be updated as well
 * b :: b_value = new_value
 *
 * # a_new_value should contain `new_value`
 * a_new_value = b as A :: a_value
 * }</pre>
 */
@Getter
@Setter
@ToString
public class ValueReference implements Expression {
    private Value<?> value;

    private ValueReference(Value<?> value) {
        this.value = value;
    }

    public static ValueReference instanceOf(Expression expression) {
        if (expression instanceof ValueReference) {
            // reuse variable
            return (ValueReference) expression;
        } else {
            Value<?> value = expression.evaluate();
            if (value == null) return null;
            return new ValueReference(value);
        }
    }

    @Override
    public Value<?> evaluate() {
        return value;
    }
}
