package _lib_language_generator_.structures.bnf.productions;

import _lib_language_generator_.structures.bnf.BNF;
import _lib_language_generator_.structures.bnf.productions.misc.elements.NonterminalOrChar;

import java.util.Set;

/**
 * The Backus-Naur form single elements production(s) interface.
 * BNF_BasicProduction is a main implementation of this interface.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public interface BNF_Production {
	Set<? extends NonterminalOrChar> getFirst(BNF bnf);
}
