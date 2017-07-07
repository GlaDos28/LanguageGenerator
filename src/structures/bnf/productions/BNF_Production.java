package structures.bnf.productions;

import structures.bnf.BNF;
import structures.bnf.productions.misc.nonterminalOrChar.NonterminalOrChar;

import java.util.Set;

/**
 * The Backus-Naur form single nonterminal production(s) interface.
 * BNF_BasicProduction is a main implementation of this interface.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public interface BNF_Production {
	Set<? extends NonterminalOrChar> getFirst(BNF bnf);
}
