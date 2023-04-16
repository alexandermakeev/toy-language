package org.example.toylanguage.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.expression.value.Value;
import org.example.toylanguage.statement.Statement;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExceptionContext {
    @Getter
    private static Exception exception;
    private static State state = State.NONE;

    public static Value<?> raiseException(Value<?> value) {
        exception = new Exception(value, new Stack<>());
        state = State.RAISED;
        return null;
    }

    public static void rescueException() {
        exception = null;
        state = State.NONE;
    }

    public static void disable() {
        state = State.DISABLED;
    }

    public static void enable() {
        state = State.RAISED;
    }

    public static boolean isRaised() {
        return state == State.RAISED;
    }

    public static void addTracedStatement(Statement statement) {
        if (isRaised()) {
            exception.getStackTrace().add(statement);
        }
    }

    public static void printStackTrace() {
        System.err.println(exception);
        rescueException();
    }

    @RequiredArgsConstructor
    @Getter
    public static class Exception {
        private final Value<?> value;
        private final List<Statement> stackTrace;

        @Override
        public String toString() {
            return value + "\n" + stackTrace
                    .stream()
                    .map(st -> String.format("    at %s:%d", st.getBlockName(), st.getRowNumber()))
                    .collect(Collectors.joining("\n"));
        }
    }

    private enum State {
        NONE,
        RAISED,
        DISABLED
    }
}
