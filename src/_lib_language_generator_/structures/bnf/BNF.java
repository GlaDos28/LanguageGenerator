package _lib_language_generator_.structures.bnf;

import _lib_language_generator_.structures.bnf.productions.BNF_BasicProduction;
import _lib_language_generator_.structures.bnf.productions.BNF_LexemeProduction;
import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_NT;

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
		this.nonterminals   = new ArrayList<>();
		this.lexemes        = new ArrayList<>();
	}

	public BNF_BasicProduction getNonterminal(int nonterminalId) {
		return this.nonterminals.get(nonterminalId);
	}

	public BNF_BasicProduction getNonterminal(BNF_NT nonterminalId) {
		return this.getNonterminal(nonterminalId.get());
	}

	public int getNonterminalNum() {
		return this.nonterminals.size();
	}

	//**

	public BNF_LexemeProduction getLexeme(int lexemeId) {
		return (BNF_LexemeProduction) this.getNonterminal(lexemeId);
	}

	public BNF_LexemeProduction getLexeme(BNF_NT lexemeId) {
		return this.getLexeme(lexemeId.get());
	}

	public BNF_LexemeProduction getLexemeFromList(int lexemeIndex) {
		return this.lexemes.get(lexemeIndex);
	}

	public int getLexemeNum() {
		return this.lexemes.size();
	}

	//**

	public int addNonterminal(BNF_BasicProduction nonterminal) {
		this.nonterminals.add(nonterminal);
		return this.nonterminals.size() - 1;
	}

	public int addLexeme(BNF_LexemeProduction lexeme) {
		this.lexemes.add(lexeme);
		return this.addNonterminal(lexeme);
	}

	//**

	public final BNF_BasicProduction getFirstNonterminal() {
		return (this.nonterminals.isEmpty()) ?
			null :
			this.nonterminals.get(0);
	}

	//**

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
