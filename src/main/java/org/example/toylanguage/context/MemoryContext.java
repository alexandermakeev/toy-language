package org.example.toylanguage.context;

/**
 * Memory context management to isolate locally defined variables
 */
public class MemoryContext {
	private static MemoryScope memory = new MemoryScope(null);

	public static MemoryScope getMemory() {
		return memory;
	}

	public static void newScope() {
		MemoryContext.memory = new MemoryScope(memory);
	}

	public static void endScope() {
		MemoryContext.memory = memory.getParent();
	}
}
