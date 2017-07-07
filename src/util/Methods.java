package util;

/**
 * Utility common methods.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class Methods {
	private Methods() {}

	public static String[][] getTwoDimWithOneElement(String[] array) {
		String[][] resArray = new String[array.length][];

		for (int i = 0; i < array.length; i++)
			resArray[i] = new String[] { array[i] };

		return resArray;
	}
}
