package structures.bnf.productions.misc.nonterminalOrChar;

import structures.bnf.productions.misc.NTStrChr;

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
	public NTStrChr getType() {
		return NTStrChr.CHAR;
	}

	@Override
	public String toString() {
		return String.valueOf(this.chr);
	}
}
