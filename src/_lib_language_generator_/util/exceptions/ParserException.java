package _lib_language_generator_.util.exceptions;

import _lib_language_generator_.structures.token.Token;

/**
 * Exceptions thrown by a syntactical analyzer.
 *
 * @author GlaDos
 * @since 08.07.17
 */
public final class ParserException extends Exception {
	private final Token token;

	public ParserException() {
		this(null, null);
	}

	public ParserException(Token token) {
		this(null, token);
	}

	public ParserException(String msg, Token token) {
		super((msg == null) ?
			"unexpected token: " + token :
			msg
		);

		this.token = token;
	}

	public Token getToken() {
		return this.token;
	}

	@Override
	public String toString() {
		return "error in parser: unexpected token: " + this.token;
	}
}
