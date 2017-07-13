package _lib_language_generator_.structures.bnf.productions.misc.elements;

import _lib_language_generator_.structures.token.Token;
import _lib_language_generator_.util.SemanticRule;

import java.util.ArrayList;

/**
 * Semantic rule as an element in elements production.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class BNF_SRule implements Element {
	private final SemanticRule semanticRule;

	public BNF_SRule(SemanticRule semanticRule) {
		this.semanticRule = semanticRule;
	}

	public Runnable extractRunnable(ArrayList<Token> tokens) {
		return this.semanticRule.extractRunnable(tokens.toArray(new Token[0]));
	}

	@Override
	public ElemType getType() {
		return ElemType.SEMANTIC_RULE;
	}

	@Override
	public String toString() {
		return "semantic rule { " + this.semanticRule + " }";
	}
}
