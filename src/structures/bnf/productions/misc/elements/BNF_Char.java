package structures.bnf.productions.misc.elements;

/**
 * Char as a terminal.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_Char implements NonterminalOrChar {
	private final char chr;

	public BNF_Char(char chr) {
		this.chr = chr;
	}

	public char get() {
		return this.chr;
	}

	@Override
	public ElemType getType() {
		return ElemType.CHAR;
	}

	@Override
	public String toString() {
		return String.valueOf(this.chr);
	}
}
