package structures.bnf.productions.misc.elements;

/**
 * Semantic rule as an element in elements production.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_SRule implements Element {
	private final Runnable runnable;

	public BNF_SRule(Runnable runnable) {
		this.runnable = runnable;
	}

	public void executeRule() {
		this.runnable.run();
	}

	@Override
	public ElemType getType() {
		return ElemType.SEMANTIC_RULE;
	}

	@Override
	public String toString() {
		return "semantic rule <" + runnable + ">";
	}
}
