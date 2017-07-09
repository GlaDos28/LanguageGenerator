package generated;

import structures.bnf.BNF;
import structures.bnf.productions.BNF_BasicProduction;
import structures.bnf.productions.BNF_LexemeProduction;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.BNF_String;
import structures.bnf.productions.misc.elements.Element;

public final class BNF_Generator {
	public static BNF getBNF() {
		BNF bnf = new BNF();

		//** nonterminals declaration

		int _D_ = bnf.addNonterminal(new BNF_BasicProduction("D"));

		int _A_ = bnf.addLexeme(new BNF_LexemeProduction("A"));
		int _B_ = bnf.addLexeme(new BNF_LexemeProduction("B"));
		int _C_ = bnf.addLexeme(new BNF_LexemeProduction("C"));

		//** nonterminals products implementation

		bnf.getNonterminal(_D_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_A_),
				new BNF_NT(_A_)
			},
			new Element[] {

			}
		});

		bnf.getLexeme(_A_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_B_),
				new BNF_String("and"),
				new BNF_NT(_C_)
			}
		});

		bnf.getLexeme(_B_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("hello")
			}
		});

		bnf.getLexeme(_C_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("world!")
			}
		});

		return bnf;
	}

	private BNF_Generator() {}
}
