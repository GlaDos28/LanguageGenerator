package structures.bnf;

import structures.bnf.productions.BNF_BasicProduction;
import structures.bnf.productions.BNF_LexemeProduction;
import structures.bnf.productions.misc.BNF_NT;

import java.util.ArrayList;

/**
 * Implementation of the Backus-Naur form of grammar definition.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class BNF {
	private final ArrayList<BNF_BasicProduction>  nonterminals;
	private final ArrayList<BNF_LexemeProduction> lexemes; /* all of these ones are also contained in nonterminals */

	public BNF() {
		this.nonterminals = new ArrayList<>();
		this.lexemes      = new ArrayList<>();
	}

	public BNF_BasicProduction getNonterminal(int nonterminalId) {
		return this.nonterminals.get(nonterminalId);
	}

	public BNF_BasicProduction getNonterminal(BNF_NT nonterminalId) {
		return this.getNonterminal(nonterminalId.get());
	}

	public BNF_LexemeProduction getLexeme(int lexemeId) {
		return (BNF_LexemeProduction) this.getNonterminal(lexemeId);
	}

	public BNF_LexemeProduction getLexeme(BNF_NT lexemeId) {
		return this.getLexeme(lexemeId.get());
	}

	public int addNonterminal(BNF_BasicProduction nonterminal) {
		this.nonterminals.add(nonterminal);
		return this.nonterminals.size() - 1;
	}

	public int addLexeme(BNF_LexemeProduction lexeme) {
		this.lexemes.add(lexeme);
		return addNonterminal(lexeme);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("BNF {\n");

		for (int i = 0; i < this.nonterminals.size(); i++)
			stringBuilder
				.append(i)
				.append(": ")
				.append(this.nonterminals.get(i))
				.append('\n');

		return stringBuilder.append('}').toString();
	}
}
