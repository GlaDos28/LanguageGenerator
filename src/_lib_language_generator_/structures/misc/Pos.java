package _lib_language_generator_.structures.misc;

/**
 * Position in the text with a line and a column.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class Pos {
	private final int line, column;

	public Pos(int line, int column) {
		this.line   = line;
		this.column = column;
	}

	public int getLine() {
		return this.line;
	}

	public int getColumn() {
		return this.column;
	}

	@Override
	public String toString() {
		return "(" + this.line + ", " + this.column + ")";
	}
}
