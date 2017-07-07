package structures.bnf.productions.misc;

import structures.bnf.productions.misc.nonterminalOrChar.NonterminalOrChar;
import structures.bnf.productions.misc.nonterminalOrString.NonterminalOrString;

/**
 * Nonterminal as an element in nonterminal productions.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_NT implements NonterminalOrString, NonterminalOrChar {
	private final int num;

	public BNF_NT(int num) {
		this.num = num;
	}

	public int get() {
		return this.num;
	}

	@Override
	public NTStrChr getType() {
		return NTStrChr.NONTERMINAL;
	}

	@Override
	public String toString() {
		return String.valueOf(this.num);
	}
}
