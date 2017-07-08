package util.exceptions;

import structures.misc.Pos;

/**
 * Exceptions thrown by a lexeme analyzer.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class LexerException extends Exception {
	private final char symbol;
	private final Pos  pos;

	public LexerException(char symbol, Pos pos) {
		this(null, symbol, pos);
	}

	public LexerException(String msg, char symbol, Pos pos) {
		super((msg == null) ?
			"unexpected symbol \'" + symbol + "\' in the position " + pos :
			msg
		);

		this.symbol = symbol;
		this.pos    = pos;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public Pos getPos() {
		return this.pos;
	}

	@Override
	public String toString() {
		return "error in lexer: unexpected symbol \'" + this.symbol + "\' in the position " + this.pos;
	}
}
