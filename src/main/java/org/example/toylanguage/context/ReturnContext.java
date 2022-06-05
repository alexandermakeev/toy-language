package org.example.toylanguage.context;

public class ReturnContext {
	private static ReturnScope scope = new ReturnScope();

	public static ReturnScope getScope() {
		return scope;
	}

	public static void reset() {
		ReturnContext.scope = new ReturnScope();
	}
}
