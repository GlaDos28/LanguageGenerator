package _lib_language_generator_.structures.token;

import _lib_language_generator_.structures.misc.Pos;

/**
 * Structure of a single token.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class Token {
	private final Pos    pos;
	private final String type;
	private final String value;

	public Token(Pos pos, String type, String value) {
		this.pos   = pos;
		this.type  = type;
		this.value = value;
	}

	public Pos getPos() {
		return this.pos;
	}

	public String getType() {
		return this.type;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "token { pos: " + this.pos + ", type: " + this.type + ", value: " + this.value + " }";
	}
}
