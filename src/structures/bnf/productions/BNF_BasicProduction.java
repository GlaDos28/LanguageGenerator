package structures.bnf.productions;

import structures.bnf.BNF;
import structures.bnf.productions.misc.BNF_NT;
import structures.bnf.productions.misc.NTStrChr;
import structures.bnf.productions.misc.nonterminalOrChar.NonterminalOrChar;
import structures.bnf.productions.misc.nonterminalOrString.NonterminalOrString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The Backus-Naur form single nonterminal production(s).
 *
 * @author GlaDos
 * @since 03.07.17
 */
public class BNF_BasicProduction implements BNF_Production {
	public static final BNF_BasicProduction EPSILON = new BNF_BasicProduction("", new ArrayList<>());

	protected final String  name;
	protected final NonterminalOrString[][] productionsElements; /* two-dim because of | operation */

	public BNF_BasicProduction(String name, NonterminalOrString[][] productionsElements) {
		this.name = name;
		this.productionsElements = productionsElements;
	}

	public BNF_BasicProduction(String name, ArrayList<ArrayList<NonterminalOrString>> statementElements) {
		this.name = name;
		this.productionsElements = new NonterminalOrString[statementElements.size()][];

		for (int i = 0; i < statementElements.size(); i++) {
			ArrayList<NonterminalOrString> statementElementsOption = statementElements.get(i);
			this.productionsElements[i] = new NonterminalOrString[statementElementsOption.size()];

			for (int j = 0; j < statementElementsOption.size(); j++)
				this.productionsElements[i][j] = statementElementsOption.get(j);
		}
	}

	@Override
	public Set<NonterminalOrChar> getFirst(BNF bnf) { /* actually Set<BNF_NT> */
		Set<NonterminalOrChar> res = new HashSet<>();

		for (NonterminalOrString[] productionElements : this.productionsElements) {
			NonterminalOrString firstElement = productionElements[0];

			if (!NTStrChr.NONTERMINAL.equals(firstElement.getType()))
				throw new RuntimeException("nonterminal production can not have string elements");

			BNF_BasicProduction firstElementDef = bnf.getNonterminal(((BNF_NT) firstElement).get());

			if (firstElementDef instanceof BNF_LexemeProduction)
				res.add((BNF_NT) firstElement);
			else
				res.addAll(firstElementDef.getFirst(bnf));
		}

		return res;
	}

	@Override
	public final String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder
			.append("BNF productions { <")
			.append(this.name)
			.append("> ::= ");

		for (int i = 0; i < this.productionsElements.length - 1; i++) {
			NonterminalOrString[] productsOption = this.productionsElements[i];

			for (NonterminalOrString product : productsOption)
				stringBuilder.append(product).append(' ');

			stringBuilder.append("| ");
		}

		if (this.productionsElements.length > 0) {
			NonterminalOrString[] productionsOption = this.productionsElements[this.productionsElements.length - 1];

			for (NonterminalOrString production : productionsOption)
				stringBuilder.append(production).append(' ');
		}

		return stringBuilder.append(". }").toString();
	}
}
