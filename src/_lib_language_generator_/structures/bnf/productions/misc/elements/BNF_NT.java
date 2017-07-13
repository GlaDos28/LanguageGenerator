package _lib_language_generator_.structures.bnf.productions.misc.elements;

/**
 * Element as an element in elements productions.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_NT implements Element, NonterminalOrChar {
	private final int num;

	public BNF_NT(int num) {
		this.num = num;
	}

	public int get() {
		return this.num;
	}

	@Override
	public ElemType getType() {
		return ElemType.NONTERMINAL;
	}

	@Override
	public String toString() {
		return String.valueOf(this.num);
	}
}
