package util;

import structures.token.Token;

/**
 * Extracts Runnable with the given processed tokens in syntactic tree as arguments.
 *
 * @author GlaDos
 * @since 09.07.17
 */
@FunctionalInterface
public interface SemanticRule {
	Runnable extractRunnable(Token[] tokens);
}
