package structures.bnf.productions;

import structures.bnf.BNF;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.ElemType;
import structures.bnf.productions.misc.elements.Element;
import structures.bnf.productions.misc.elements.NonterminalOrChar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The Backus-Naur form single elements production(s).
 *
 * @author GlaDos
 * @since 03.07.17
 */
public class BNF_BasicProduction implements BNF_Production {
	protected final String name;
	protected Element[][]  productionsElements; /* two-dim because of | operation */

	public BNF_BasicProduction(String name) {
		this(name, (Element[][]) null);
	}

	public BNF_BasicProduction(String name, Element[][] productionsElements) {
		this.name = name;
		this.setElements(productionsElements);
	}

	public BNF_BasicProduction(String name, ArrayList<ArrayList<Element>> productionsElements) {
		this.name = name;
		this.setElements(productionsElements);
	}

	public final String getName() {
		return this.name;
	}

	public final Element[][] getProductionsElements() {
		return this.productionsElements;
	}

	public final void setElements(Element[][] productionsElements) {
		this.productionsElements = productionsElements;
	}

	public final void setElements(ArrayList<ArrayList<Element>> productionElements) {
		this.productionsElements = new Element[productionElements.size()][];

		for (int i = 0; i < productionElements.size(); i++) {
			ArrayList<Element> statementElementsOption = productionElements.get(i);
			this.productionsElements[i] = new Element[statementElementsOption.size()];

			for (int j = 0; j < statementElementsOption.size(); j++)
				this.productionsElements[i][j] = statementElementsOption.get(j);
		}
	}

	@Override
	public Set<NonterminalOrChar> getFirst(BNF bnf) { /* actually Set<BNF_NT> */
		Set<NonterminalOrChar> res = new HashSet<>();

		for (Element[] productionElements : this.productionsElements) {
			int i = 0;
			for (; i < productionElements.length && ElemType.SEMANTIC_RULE.equals(productionElements[i].getType()); i++);

			if (i >= productionElements.length)
				continue;

			Element firstElement = productionElements[i];

			if (!ElemType.NONTERMINAL.equals(firstElement.getType()))
				throw new RuntimeException("elements production can not have string elements");

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
			Element[] productsOption = this.productionsElements[i];

			for (Element product : productsOption)
				stringBuilder.append(product).append(' ');

			stringBuilder.append("| ");
		}

		if (this.productionsElements.length > 0) {
			Element[] productionsOption = this.productionsElements[this.productionsElements.length - 1];

			for (Element production : productionsOption)
				stringBuilder.append(production).append(' ');
		}

		return stringBuilder.append(". }").toString();
	}
}
