package _lib_language_generator_.util;

import javafx.util.Pair;

/**
 * Utility common methods.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class Methods {
	private Methods() {}

	public static String getUnescapedString(String s) {
		return getUnescapedString(s, '\\', new Pair[] { new Pair<>('\'', '\''), new Pair<>('"', '"'), new Pair<>('n', '\n'), new Pair<>('t', '\t'), new Pair<>('\\', '\\') });
	}

	public static String getUnescapedString(String s, char escapeSymbol, Pair<Character, Character>[] escapeChars) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == escapeSymbol) {
				i++;

				if (i >= s.length())
					throw new RuntimeException("invalid string \"" + s + "\": no more characters after '" + escapeSymbol + "' symbol");

				boolean found = false;

				for (Pair<Character, Character> escapeChar : escapeChars)
					if (s.charAt(i) == escapeChar.getKey()) {
						found = true;
						builder.append(escapeChar.getValue());
					}

				if (!found)
					throw new RuntimeException("invalid string \"" + s + "\": unknown escape sequence \"" + escapeSymbol + s.charAt(i) + "\" at position " + i);
			}
			else
				builder.append(s.charAt(i));
		}

		return builder.toString();
	}

	public static String getEscapedString(String s) {
		return getEscapedString(s, '\\', new Pair[] { new Pair<>('\'', '\''), new Pair<>('"', '"'), new Pair<>('\n', 'n'), new Pair<>('\t', 't'), new Pair<>('\\', '\\') });
	}

	public static String getEscapedString(String s, char escapeSymbol, Pair<Character, Character>[] escapeChars) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			boolean found = false;

			for (Pair<Character, Character> escapeChar : escapeChars)
				if (s.charAt(i) == escapeChar.getKey()) {
					found = true;
					builder.append(escapeSymbol).append(escapeChar.getValue());
				}

			if (!found)
				builder.append(s.charAt(i));
		}

		return builder.toString();
	}
}
