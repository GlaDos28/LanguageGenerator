package launching;

import javafx.util.Pair;
import structures.bnf.BNF;
import structures.bnf.productions.BNF_BasicProduction;
import structures.bnf.productions.BNF_LexemeProduction;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.BNF_SRule;
import structures.bnf.productions.misc.elements.BNF_String;
import structures.bnf.productions.misc.elements.Element;
import util.Methods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Backus-Naur form language generator. Generates java code that can be used as BNF via this program.
 *
 * @author GlaDos
 * @since 09.07.17
 */
final class BNF_Creator {
	static BNF getBNF(String outPath, String className, String packageName) { /* The BNF of BNFs */
		//** initialization

		StringBuilder builder = new StringBuilder();
		BNF bnf = new BNF();
		final ArrayList<Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>>> nonterminals = new ArrayList<>();

		//** nonterminals declaration

		int bnfId            = bnf.addNonterminal(new BNF_BasicProduction("BNF"));
		int nonterminalsId   = bnf.addNonterminal(new BNF_BasicProduction("Nonterminals"));
		int nonterminalId    = bnf.addNonterminal(new BNF_BasicProduction("Nonterminal"));
		int productionsId    = bnf.addNonterminal(new BNF_BasicProduction("Productions"));
		int optProductionsId = bnf.addNonterminal(new BNF_BasicProduction("OptProductions"));
		int productionId     = bnf.addNonterminal(new BNF_BasicProduction("Production"));

		int identifierId     = bnf.addLexeme(new BNF_LexemeProduction("Identifier"));
		int stringId         = bnf.addLexeme(new BNF_LexemeProduction("String"));
		int optIdentifierId  = bnf.addLexeme(new BNF_LexemeProduction("OptIdentifier"));
		int dollarId         = bnf.addLexeme(new BNF_LexemeProduction("Dollar"));
		int orId             = bnf.addLexeme(new BNF_LexemeProduction("Or"));
		int openSignId       = bnf.addLexeme(new BNF_LexemeProduction("OpenSign"));
		int closeSignId      = bnf.addLexeme(new BNF_LexemeProduction("CloseSign"));
		int assignId         = bnf.addLexeme(new BNF_LexemeProduction("Assign"));
		int dotId            = bnf.addLexeme(new BNF_LexemeProduction("Dot"));
		int optStringId      = bnf.addLexeme(new BNF_LexemeProduction("OptString"));
		int symbolId         = bnf.addLexeme(new BNF_LexemeProduction("Symbol"));
		int letterId         = bnf.addLexeme(new BNF_LexemeProduction("Letter"));
		int digitId          = bnf.addLexeme(new BNF_LexemeProduction("Digit"));

		//** nonterminals products implementation

		bnf.getNonterminal(bnfId).setElements(new Element[][] {
			new Element[] {
				new BNF_SRule(tokens -> () -> builder
					.append("package ")
					.append(packageName)
					.append(
						";\n\n" +
						"import structures.bnf.BNF;\n" +
						"import structures.bnf.productions.BNF_BasicProduction;\n" +
						"import structures.bnf.productions.BNF_LexemeProduction;\n" +
						"import structures.bnf.productions.misc.elements.BNF_NT;\n" +
						"import structures.bnf.productions.misc.elements.BNF_String;\n" +
						"import structures.bnf.productions.misc.elements.Element;\n\n" +
						"public final class ")
					.append(className)
					.append(
						" {\n\tpublic static BNF getBNF() {\n" +
						"\t\tBNF bnf = new BNF();\n\n" +
						"\t\t//** nonterminals declaration\n\n")
				),
				new BNF_NT(nonterminalsId),
				new BNF_SRule(tokens -> () -> {
					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals)
						if (!nonterminal.getKey().getValue())
							builder
								.append("\t\tint _")
								.append(nonterminal.getKey().getKey())
								.append("_ = bnf.addNonterminal(new BNF_BasicProduction(\"")
								.append(nonterminal.getKey().getKey())
								.append("\"));\n");

					builder.append('\n');

					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals)
						if (nonterminal.getKey().getValue())
							builder
								.append("\t\tint _")
								.append(nonterminal.getKey().getKey())
								.append("_ = bnf.addLexeme(new BNF_LexemeProduction(\"")
								.append(nonterminal.getKey().getKey())
								.append("\"));\n");

					builder.append("\n\t\t//** nonterminals products implementation\n\n");

					//** nonterminals

					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals) {
						if (nonterminal.getKey().getValue())
							continue;

						builder
							.append("\t\tbnf.getNonterminal(_")
							.append(nonterminal.getKey().getKey())
							.append("_).setElements(new Element[][] {\n");

						for (ArrayList<Pair<String, Boolean>> prod : nonterminal.getValue()) {
							builder.append("\t\t\tnew Element[] {\n");

							for (Pair<String, Boolean> elem : prod)
								builder
									.append("\t\t\t\tnew BNF_NT(_")
									.append(elem.getKey())
									.append("_),\n");

							if (!prod.isEmpty())
								builder.delete(builder.length() - 2, builder.length()); /* removes redundant comma */

							builder.append("\n\t\t\t},\n");
						}

						builder.delete(builder.length() - 2, builder.length()); /* removes redundant comma */
						builder.append("\n\t\t});\n\n");
					}

					//** lexemes

					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals) {
						if (!nonterminal.getKey().getValue())
							continue;

						builder
							.append("\t\tbnf.getLexeme(_")
							.append(nonterminal.getKey().getKey())
							.append("_).setElements(new Element[][] {\n");

						for (ArrayList<Pair<String, Boolean>> prod : nonterminal.getValue()) {
							builder.append("\t\t\tnew Element[] {\n");

							for (Pair<String, Boolean> elem : prod)
								if (elem.getValue())
									builder
										.append("\t\t\t\tnew BNF_NT(_")
										.append(elem.getKey())
										.append("_),\n");
								else
									builder
										.append("\t\t\t\tnew BNF_String(\"")
										.append(elem.getKey())
										.append("\"),\n");

							if (!prod.isEmpty())
								builder.delete(builder.length() - 2, builder.length()); /* removes redundant comma */

							builder.append("\n\t\t\t},\n");
						}

						builder.delete(builder.length() - 2, builder.length()); /* removes redundant comma */
						builder.append("\n\t\t});\n\n");
					}

					if (!nonterminals.isEmpty())
						builder.delete(builder.length() - 1, builder.length()); /* removes redundant newline */

					//**

					builder
						.append("\n\t\treturn bnf;\n\t}\n\n\tprivate ")
						.append(className)
						.append("() {}\n}\n");

					try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPath))) {
						writer.write(builder.toString());
					}
					catch (IOException ex) {
						System.out.println(ex.getMessage());
					}
				})
			}
		});

		bnf.getNonterminal(nonterminalsId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(nonterminalId),
				new BNF_NT(nonterminalsId)
			},
			new Element[] {}
		});

		bnf.getNonterminal(nonterminalId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(openSignId),
				new BNF_NT(dollarId),
				new BNF_NT(identifierId),
				new BNF_SRule(tokens -> () -> nonterminals.add(new Pair<>(new Pair<>(tokens[2].getValue(), true), new ArrayList<>()))),
				new BNF_NT(closeSignId),
				new BNF_NT(assignId),
				new BNF_NT(productionsId),
				new BNF_NT(dotId)
			},
			new Element[] {
				new BNF_NT(openSignId),
				new BNF_NT(identifierId),
				new BNF_SRule(tokens -> () -> nonterminals.add(new Pair<>(new Pair<>(tokens[1].getValue(), false), new ArrayList<>()))),
				new BNF_NT(closeSignId),
				new BNF_NT(assignId),
				new BNF_NT(productionsId),
				new BNF_NT(dotId)
			},
		});

		bnf.getNonterminal(productionsId).setElements(new Element[][] {
			new Element[] {
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().add(new ArrayList<>())),
				new BNF_NT(productionId),
				new BNF_NT(optProductionsId)
			}
		});

		bnf.getNonterminal(optProductionsId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(orId),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().add(new ArrayList<>())),
				new BNF_NT(productionId),
				new BNF_NT(optProductionsId)
			},
			new Element[] {}
		});

		bnf.getNonterminal(productionId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(openSignId),
				new BNF_NT(identifierId),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().get(nonterminals.get(nonterminals.size() - 1).getValue().size() - 1).add(new Pair<>(tokens[1].getValue(), true))),
				new BNF_NT(closeSignId),
				new BNF_NT(productionId)
			},
			new Element[] {
				new BNF_NT(stringId),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().get(nonterminals.get(nonterminals.size() - 1).getValue().size() - 1).add(new Pair<>(Methods.getEscapedString(Methods.getUnescapedString(tokens[0].getValue().substring(1, tokens[0].getValue().length() - 1))), false))),
				new BNF_NT(productionId)
			},
			new Element[] {}
		});

		//** lexemes

		bnf.getLexeme(identifierId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(letterId),
				new BNF_NT(optIdentifierId)
			},
			new Element[] {
				new BNF_String("_"),
				new BNF_NT(optIdentifierId)
			}
		});

		bnf.getLexeme(stringId).setElements(new Element[][] {
			new Element[] {
				new BNF_String("\""),
				new BNF_NT(optStringId),
				new BNF_String("\"")
			}
		});

		bnf.getLexeme(optIdentifierId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(letterId),
				new BNF_NT(optIdentifierId)
			},
			new Element[] {
				new BNF_NT(digitId),
				new BNF_NT(optIdentifierId)
			},
			new Element[] {
				new BNF_String("_"),
				new BNF_NT(optIdentifierId)
			},
			new Element[] {}
		});

		bnf.getLexeme(optStringId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(symbolId),
				new BNF_NT(optStringId)
			},
			new Element[] {}
		});

		bnf.getLexeme(dollarId).setElements(new Element[][] {
			new Element[] { new BNF_String("$") }
		});

		bnf.getLexeme(orId).setElements(new Element[][] {
			new Element[] { new BNF_String("|") }
		});

		bnf.getLexeme(openSignId).setElements(new Element[][] {
			new Element[] { new BNF_String("<") }
		});

		bnf.getLexeme(closeSignId).setElements(new Element[][] {
			new Element[] { new BNF_String(">") }
		});

		bnf.getLexeme(assignId).setElements(new Element[][] {
			new Element[] { new BNF_String("::=") }
		});

		bnf.getLexeme(dotId).setElements(new Element[][] {
			new Element[] { new BNF_String(".") }
		});

		bnf.getLexeme(symbolId).setElements(new Element[][] {
			new Element[] { new BNF_NT(digitId) },
			new Element[] { new BNF_NT(letterId) },
			new Element[] { new BNF_String("/") },
			new Element[] { new BNF_String("?") },
			new Element[] { new BNF_String(",") },
			new Element[] { new BNF_String("'") },
			new Element[] { new BNF_String("\\\"") },
			new Element[] { new BNF_String(";") },
			new Element[] { new BNF_String("\\\\") },
			new Element[] { new BNF_String("]") },
			new Element[] { new BNF_String("}") },
			new Element[] { new BNF_String("[") },
			new Element[] { new BNF_String("{") },
			new Element[] { new BNF_String("=") },
			new Element[] { new BNF_String("+") },
			new Element[] { new BNF_String("-") },
			new Element[] { new BNF_String("_") },
			new Element[] { new BNF_String(")") },
			new Element[] { new BNF_String("(") },
			new Element[] { new BNF_String("*") },
			new Element[] { new BNF_String("&") },
			new Element[] { new BNF_String("^") },
			new Element[] { new BNF_String("%") },
			new Element[] { new BNF_String("#") },
			new Element[] { new BNF_String("№") },
			new Element[] { new BNF_String("@") },
			new Element[] { new BNF_String("!") },
			new Element[] { new BNF_String("`") },
			new Element[] { new BNF_String("~") },
			new Element[] { new BNF_String("$") },
			new Element[] { new BNF_String("|") },
			new Element[] { new BNF_String("<") },
			new Element[] { new BNF_String(">") },
			new Element[] { new BNF_String(":") },
			new Element[] { new BNF_String(".") }
		});

		bnf.getLexeme(letterId).setElements(new Element[][] {
			new Element[] { new BNF_String("a") },
			new Element[] { new BNF_String("b") },
			new Element[] { new BNF_String("c") },
			new Element[] { new BNF_String("d") },
			new Element[] { new BNF_String("e") },
			new Element[] { new BNF_String("f") },
			new Element[] { new BNF_String("g") },
			new Element[] { new BNF_String("h") },
			new Element[] { new BNF_String("i") },
			new Element[] { new BNF_String("j") },
			new Element[] { new BNF_String("k") },
			new Element[] { new BNF_String("l") },
			new Element[] { new BNF_String("m") },
			new Element[] { new BNF_String("n") },
			new Element[] { new BNF_String("o") },
			new Element[] { new BNF_String("p") },
			new Element[] { new BNF_String("q") },
			new Element[] { new BNF_String("r") },
			new Element[] { new BNF_String("s") },
			new Element[] { new BNF_String("t") },
			new Element[] { new BNF_String("u") },
			new Element[] { new BNF_String("v") },
			new Element[] { new BNF_String("w") },
			new Element[] { new BNF_String("x") },
			new Element[] { new BNF_String("y") },
			new Element[] { new BNF_String("z") },
			new Element[] { new BNF_String("A") },
			new Element[] { new BNF_String("B") },
			new Element[] { new BNF_String("C") },
			new Element[] { new BNF_String("D") },
			new Element[] { new BNF_String("E") },
			new Element[] { new BNF_String("F") },
			new Element[] { new BNF_String("G") },
			new Element[] { new BNF_String("H") },
			new Element[] { new BNF_String("I") },
			new Element[] { new BNF_String("J") },
			new Element[] { new BNF_String("K") },
			new Element[] { new BNF_String("L") },
			new Element[] { new BNF_String("M") },
			new Element[] { new BNF_String("N") },
			new Element[] { new BNF_String("O") },
			new Element[] { new BNF_String("P") },
			new Element[] { new BNF_String("Q") },
			new Element[] { new BNF_String("R") },
			new Element[] { new BNF_String("S") },
			new Element[] { new BNF_String("T") },
			new Element[] { new BNF_String("U") },
			new Element[] { new BNF_String("V") },
			new Element[] { new BNF_String("W") },
			new Element[] { new BNF_String("X") },
			new Element[] { new BNF_String("Y") },
			new Element[] { new BNF_String("Z") }
		});

		bnf.getLexeme(digitId).setElements(new Element[][] {
			new Element[] { new BNF_String("0") },
			new Element[] { new BNF_String("1") },
			new Element[] { new BNF_String("2") },
			new Element[] { new BNF_String("3") },
			new Element[] { new BNF_String("4") },
			new Element[] { new BNF_String("5") },
			new Element[] { new BNF_String("6") },
			new Element[] { new BNF_String("7") },
			new Element[] { new BNF_String("8") },
			new Element[] { new BNF_String("9") }
		});

		return bnf;
	}

	private BNF_Creator() {}
}
