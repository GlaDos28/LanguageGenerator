package structures.bnf.productions.misc.nonterminalOrString;

import structures.bnf.productions.misc.NTStrChr;

/**
 * String as an element in nonterminal productions.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_String implements NonterminalOrString {
	private final String str;

	public BNF_String(String str) {
		this.str = str;
	}

	public String get() {
		return this.str;
	}

	@Override
	public NTStrChr getType() {
		return NTStrChr.STRING;
	}

	@Override
	public String toString() {
		return this.str;
	}
}
