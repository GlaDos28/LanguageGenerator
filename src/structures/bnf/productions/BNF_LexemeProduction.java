package structures.bnf.productions;

import structures.bnf.BNF;
import structures.bnf.productions.misc.BNF_NT;
import structures.bnf.productions.misc.NTStrChr;
import structures.bnf.productions.misc.nonterminalOrChar.BNF_Char;
import structures.bnf.productions.misc.nonterminalOrChar.NonterminalOrChar;
import structures.bnf.productions.misc.nonterminalOrString.BNF_String;
import structures.bnf.productions.misc.nonterminalOrString.NonterminalOrString;

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
	public static final BNF_LexemeProduction EPSILON = new BNF_LexemeProduction("", new ArrayList<>());

	public BNF_LexemeProduction(String name, NonterminalOrString[][] productionsElements) {
		super(name, productionsElements);
	}

	public BNF_LexemeProduction(String name, ArrayList<ArrayList<NonterminalOrString>> productionsElements) {
		super(name, productionsElements);
	}

	@Override
	public Set<NonterminalOrChar> getFirst(BNF bnf) { /* actually Set<BNF_Char> */
		Set<NonterminalOrChar> res = new HashSet<>();

		for (NonterminalOrString[] productionElements : this.productionsElements) {
			NonterminalOrString firstElement = productionElements[0];

			if (NTStrChr.NONTERMINAL.equals(firstElement.getType()))
				res.addAll(bnf.getLexeme((BNF_NT) firstElement).getFirst(bnf));
			else {
				String firstElementString = ((BNF_String) firstElement).get();
				res.add((firstElementString.isEmpty()) ?
					new BNF_Char('\0') : /* do not use in the grammar! */
					new BNF_Char(firstElementString.charAt(0)));
			}
		}

		return res;
	}
}
