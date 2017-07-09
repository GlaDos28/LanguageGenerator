package util;

/**
 * Utility common methods.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class Methods {
	private Methods() {}

	public static String getUnescapedString(String s) {
		StringBuilder builder = new StringBuilder();
		boolean flag = true;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\\' && flag)
				flag = false;
			else {
				builder.append(s.charAt(i));
				flag = true;
			}
		}

		return builder.toString();
	}

	public static String getEscapedString(String s) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
				case '\n':
					builder.append("\\n");
					break;
				case '\t':
					builder.append("\\t");
					break;
				case '\'':
					builder.append("\\'");
					break;
				case '\"':
					builder.append("\\\"");
					break;
				case '\\':
					builder.append("\\\\");
					break;
				default:
					builder.append(s.charAt(i));
			}
		}

		return builder.toString();
	}
}
