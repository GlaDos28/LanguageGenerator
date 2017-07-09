package structures.bnf.productions;

import structures.bnf.BNF;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.ElemType;
import structures.bnf.productions.misc.elements.BNF_Char;
import structures.bnf.productions.misc.elements.NonterminalOrChar;
import structures.bnf.productions.misc.elements.BNF_String;
import structures.bnf.productions.misc.elements.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The Backus-Naur form single lexeme production(s). Is a list of terminal (constant strings) or other lexeme productions.
 * See BNF_Production and BNF_BasicProduction for more information.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class BNF_LexemeProduction extends BNF_BasicProduction {
	public BNF_LexemeProduction(String name) {
		super(name);
	}

	public BNF_LexemeProduction(String name, Element[][] productionsElements) {
		super(name, productionsElements);
	}

	public BNF_LexemeProduction(String name, ArrayList<ArrayList<Element>> productionsElements) {
		super(name, productionsElements);
	}

	@Override
	public Set<NonterminalOrChar> getFirst(BNF bnf) { /* actually Set<BNF_Char> */
		Set<NonterminalOrChar> res = new HashSet<>();

		for (Element[] productionElements : this.productionsElements) {
			if (productionElements.length == 0)
				continue;

			Element firstElement = productionElements[0];

			if (ElemType.NONTERMINAL.equals(firstElement.getType()))
				res.addAll(bnf.getLexeme((BNF_NT) firstElement).getFirst(bnf));
			else if (ElemType.STRING.equals(firstElement.getType())) {
				String firstElementString = ((BNF_String) firstElement).get();

				if (firstElementString.isEmpty())
					throw new RuntimeException("empty terminals not allowed; for epsilon value use empty list of elements");

				res.add(new BNF_Char(firstElementString.charAt(0)));
			}
			else
				throw new RuntimeException("invalid element in lexeme production: " + firstElement.getType());
		}

		return res;
	}
}
