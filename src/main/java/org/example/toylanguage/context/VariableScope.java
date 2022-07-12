package org.example.toylanguage.context;

/**
 * Variable scope in the {@link MemoryContext}
 */
public enum VariableScope {
    /**
     * Variable can be defined and modified only within one {@link MemoryScope}
     */
    Local,
    /**
     * Variable can be modified in the inner {@link MemoryScope} blocks
     */
    Global
}
