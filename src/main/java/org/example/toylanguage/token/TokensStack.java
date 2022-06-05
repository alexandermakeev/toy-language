package org.example.toylanguage.token;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.example.toylanguage.exception.SyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TokensStack {
	private final List<Token> tokens;
	private int position;

	private static final List<TokenType> EMPTY_TOKENS = List.of(TokenType.LineBreak, TokenType.Comment);

	public Token next(TokenType type, TokenType... types) {
		skipEmptyTokens();
		TokenType[] tokenTypes = ArrayUtils.add(types, type);
		if (position < tokens.size()) {
			Token token = tokens.get(position);
			if (Stream.of(tokenTypes).anyMatch(t -> t == token.getType())) {
				position++;
				return token;
			}
		}
		throw new SyntaxException(String.format("After `%s` declaration expected any of the following lexemes `%s`", previous(), Arrays.toString(tokenTypes)));
	}

	public void back() {
		position--;
	}

	public boolean hasNext() {
		skipEmptyTokens();
		return position < tokens.size();
	}

	public Token next(TokenType type, String value) {
		skipEmptyTokens();
		if (position < tokens.size()) {
			Token token = tokens.get(position);
			if (token.getType() == type && token.getValue().equals(value)) {
				position++;
				return token;
			}
		}
		throw new SyntaxException(String.format("After `%s` declaration expected `%s, %s` lexeme", previous(), type, value));
	}

	public Token next() {
		skipEmptyTokens();
		return tokens.get(position++);
	}

	public boolean peek(TokenType type, String content) {
		skipEmptyTokens();
		return peekSameLine(type, content);
	}

	public boolean peekSameLine(TokenType type, String content) {
		if (position < tokens.size()) {
			Token token = tokens.get(position);
			return type == token.getType() && token.getValue().equals(content);
		}
		return false;
	}

	public boolean peek(TokenType type, TokenType... types) {
		skipEmptyTokens();
		return peekSameLine(type, types);
	}

	public boolean peekSameLine(TokenType type, TokenType... types) {
		if (position < tokens.size()) {
			TokenType[] tokenTypes = ArrayUtils.add(types, type);
			Token token = tokens.get(position);
			return Stream.of(tokenTypes).anyMatch(t -> t == token.getType());
		}
		return false;
	}

	private Token previous() {
		return tokens.get(position - 1);
	}

	private void skipEmptyTokens() {
		while (position != tokens.size() && EMPTY_TOKENS.contains(tokens.get(position).getType()))
			position++;
	}

}
