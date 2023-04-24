package org.example.toylanguage.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.value.TextValue;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.HandleExceptionStatement;
import org.example.toylanguage.statement.RaiseExceptionStatement;
import org.example.toylanguage.statement.Statement;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Associates thrown {@link Exception} with the current execution statement
 * <p>
 *
 * @see RaiseExceptionStatement
 * @see HandleExceptionStatement
 */
public class ExceptionContext {
    /**
     * Raised exception
     */
    @Getter
    private static Exception exception;
    /**
     * State of the exception
     */
    private static State state = State.NONE;

    /**
     * Raise an exception
     * <p>
     *
     * @param value raised value
     * @return null
     */
    public static Value<?> raiseException(Value<?> value) {
        exception = new Exception(value, new Stack<>());
        state = State.RAISED;
        return null;
    }

    /**
     * Raise an exception
     * <p>
     *
     * @param textValue raised text value
     * @return null
     */
    public static Value<?> raiseException(String textValue) {
        return raiseException(new TextValue(textValue));
    }

    /**
     * Rescue the exception if it's been handled
     */
    public static void rescueException() {
        exception = null;
        state = State.NONE;
    }

    /**
     * Disable collecting of the stack trace records before executing <strong>ensure</strong> block
     */
    public static void disable() {
        state = State.DISABLED;
    }

    /**
     * Enable collecting the stack trace after quiting the <strong>ensure</strong> block
     */
    public static void enable() {
        state = State.RAISED;
    }

    /**
     * If an exception's been raised
     */
    public static boolean isRaised() {
        return state == State.RAISED;
    }

    /**
     * Add record of the application's movement as a statement that initiated the exception
     */
    public static void addTracedStatement(Statement statement) {
        if (isRaised()) {
            exception.stackTrace.add(statement);
        }
    }

    /**
     * Print an exception
     */
    public static void printStackTrace() {
        System.err.println(exception);
        rescueException();
    }

    /**
     * Exception details
     */
    @RequiredArgsConstructor
    @Getter
    public static class Exception {
        /**
         * Raised error
         */
        private final Value<?> value;
        /**
         * Statements containing records of the application's movement leading to the statement that initiated the exception
         */
        private final List<Statement> stackTrace;

        @Override
        public String toString() {
            return String.format("%s%n%s",
                    value,
                    stackTrace
                            .stream()
                            .map(st -> String.format("%4sat %s:%d", "", st.getBlockName(), st.getRowNumber()))
                            .collect(Collectors.joining("\n"))
            );
        }
    }

    /**
     * States of the ExceptionContext
     */
    private enum State {
        /**
         * No exception raised or the exception is rescued
         */
        NONE,
        /**
         * The exception is raised
         */
        RAISED,
        /**
         * The exception disabled to execute <strong>ensure</strong> block
         */
        DISABLED
    }
}
