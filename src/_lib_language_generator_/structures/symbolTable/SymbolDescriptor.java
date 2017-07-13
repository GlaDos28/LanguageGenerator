package _lib_language_generator_.structures.symbolTable;

/**
 * Symbol description element.
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class SymbolDescriptor {
	private final String name;

	public SymbolDescriptor(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "symbol { name: " + this.name + " }";
	}
}
