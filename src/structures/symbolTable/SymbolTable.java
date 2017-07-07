package structures.symbolTable;

import java.util.ArrayList;

/**
 * SymbolDescriptor table for storing map "symbol id" : "symbol description".
 *
 * @author GlaDos
 * @since 03.07.17
 */
public final class SymbolTable {
	private final ArrayList<SymbolDescriptor> symbolMap;

	public SymbolTable() {
		this.symbolMap = new ArrayList<>();
	}

	public SymbolDescriptor getSymbolDescriptor(int symbolId) {
		return this.symbolMap.get(symbolId);
	}

	public synchronized int addSymbolDescriptor(SymbolDescriptor symbolDescriptor) {
		this.symbolMap.add(symbolDescriptor);
		return this.symbolMap.size() - 1;
	}

	public synchronized SymbolDescriptor findOrCreate(String symbolName) {
		int foundSymbolId = -1;

		for (int i = 0; i < this.symbolMap.size(); i++)
			if (symbolName.equals(this.symbolMap.get(i).getName())) {
				foundSymbolId = i;
				break;
			}

			if (foundSymbolId == -1) {
				this.addSymbolDescriptor(new SymbolDescriptor(symbolName));
				return this.symbolMap.get(this.symbolMap.size() - 1);
			}
			else
				return this.symbolMap.get(foundSymbolId);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("symbol table {\n");

		for (int i = 0; i < this.symbolMap.size(); i++)
			stringBuilder
				.append(i)
				.append(": ")
				.append(this.symbolMap.get(i))
				.append('\n');

		return stringBuilder.append('}').toString();
	}
}
