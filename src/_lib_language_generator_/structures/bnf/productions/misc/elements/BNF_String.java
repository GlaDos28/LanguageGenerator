package _lib_language_generator_.structures.bnf.productions.misc.elements;

/**
 * String as an element in elements productions.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_String implements Element {
	private final String str;

	public BNF_String(String str) {
		this.str = str;
	}

	public String get() {
		return this.str;
	}

	@Override
	public ElemType getType() {
		return ElemType.STRING;
	}

	@Override
	public String toString() {
		return this.str;
	}
}
