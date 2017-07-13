package _lib_language_generator_.launching;

import _lib_language_generator_.structures.bnf.BNF;
import _lib_language_generator_.structures.bnf.productions.BNF_BasicProduction;
import _lib_language_generator_.structures.bnf.productions.BNF_LexemeProduction;
import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_NT;
import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_SRule;
import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_String;
import _lib_language_generator_.structures.bnf.productions.misc.elements.Element;
import _lib_language_generator_.util.Methods;

import javafx.util.Pair;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class BNF_Generator {
	public static BNF getBNF(String outPath, String className, String packageName) { /* The BNF of BNFs */
		BNF bnf = new BNF();

		//** BNF initialization program fragment (semantic rule No.0)

		StringBuilder builder = new StringBuilder();
   		final ArrayList<Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>>> nonterminals = new ArrayList<>(); /* true - element, false - string, null - semantic rule */
   		final Map<String, String> semanticRules = new HashMap<>();

		//** nonterminals declaration

		int _BNF_ = bnf.addNonterminal(new BNF_BasicProduction("BNF"));
		int _Nonterminals_ = bnf.addNonterminal(new BNF_BasicProduction("Nonterminals"));
		int _Nonterminal_ = bnf.addNonterminal(new BNF_BasicProduction("Nonterminal"));
		int _Productions_ = bnf.addNonterminal(new BNF_BasicProduction("Productions"));
		int _OptProductions_ = bnf.addNonterminal(new BNF_BasicProduction("OptProductions"));
		int _Production_ = bnf.addNonterminal(new BNF_BasicProduction("Production"));
		int _Rules_ = bnf.addNonterminal(new BNF_BasicProduction("Rules"));
		int _Rule_ = bnf.addNonterminal(new BNF_BasicProduction("Rule"));

		int _Ident_ = bnf.addLexeme(new BNF_LexemeProduction("Ident"));
		int _Number_ = bnf.addLexeme(new BNF_LexemeProduction("Number"));
		int _String_ = bnf.addLexeme(new BNF_LexemeProduction("String"));
		int _RuleSymbols_ = bnf.addLexeme(new BNF_LexemeProduction("RuleSymbols"));
		int _Dollar_ = bnf.addLexeme(new BNF_LexemeProduction("Dollar"));
		int _Or_ = bnf.addLexeme(new BNF_LexemeProduction("Or"));
		int _OpenSign_ = bnf.addLexeme(new BNF_LexemeProduction("OpenSign"));
		int _CloseSign_ = bnf.addLexeme(new BNF_LexemeProduction("CloseSign"));
		int _OpenBrace_ = bnf.addLexeme(new BNF_LexemeProduction("OpenBrace"));
		int _CloseBrace_ = bnf.addLexeme(new BNF_LexemeProduction("CloseBrace"));
		int _Assign_ = bnf.addLexeme(new BNF_LexemeProduction("Assign"));
		int _Dot_ = bnf.addLexeme(new BNF_LexemeProduction("Dot"));
		int _OptIdent_ = bnf.addLexeme(new BNF_LexemeProduction("OptIdent"));
		int _OptNumber_ = bnf.addLexeme(new BNF_LexemeProduction("OptNumber"));
		int _OptString_ = bnf.addLexeme(new BNF_LexemeProduction("OptString"));
		int _OptRuleSymbols_ = bnf.addLexeme(new BNF_LexemeProduction("OptRuleSymbols"));
		int _StringSymbol_ = bnf.addLexeme(new BNF_LexemeProduction("StringSymbol"));
		int _RuleSymbol_ = bnf.addLexeme(new BNF_LexemeProduction("RuleSymbol"));
		int _Symbol_ = bnf.addLexeme(new BNF_LexemeProduction("Symbol"));
		int _Letter_ = bnf.addLexeme(new BNF_LexemeProduction("Letter"));
		int _Digit_ = bnf.addLexeme(new BNF_LexemeProduction("Digit"));

		//** nonterminals products implementation

		bnf.getNonterminal(_BNF_).setElements(new Element[][] {
			new Element[] {
				new BNF_SRule(tokens -> () -> builder
   					.append("package ")
                    .append(packageName)
                    .append(
                    	";\n\n" +
                    	"import _lib_language_generator_.structures.bnf.BNF;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.BNF_BasicProduction;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.BNF_LexemeProduction;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_NT;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_SRule;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.misc.elements.BNF_String;\n" +
                    	"import _lib_language_generator_.structures.bnf.productions.misc.elements.Element;\n\n" +
                    	"public final class ")
                    .append(className)
                    .append(
                    	" {\n\tpublic static final BNF BNF = new BNF();\n\n\tstatic {\n")
   				),
				new BNF_NT(_Nonterminals_),
				new BNF_NT(_Rules_),
				new BNF_SRule(tokens -> () -> {
   					if (semanticRules.containsKey("0"))
   						builder
   							.append("\t\t//** BNF initialization program fragment (semantic rule No.0)\n\n\t\t")
   							.append(semanticRules.get("0"))
   							.append("\n\n");

   					builder.append("\t\t//** nonterminals declaration\n\n");

   					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals)
   						if (!nonterminal.getKey().getValue())
   							builder
   								.append("\t\tint _")
   								.append(nonterminal.getKey().getKey())
   								.append("_ = BNF.addNonterminal(new BNF_BasicProduction(\"")
   								.append(nonterminal.getKey().getKey())
   								.append("\"));\n");

   					builder.append('\n');

   					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals)
   						if (nonterminal.getKey().getValue())
   							builder
   								.append("\t\tint _")
   								.append(nonterminal.getKey().getKey())
   								.append("_ = BNF.addLexeme(new BNF_LexemeProduction(\"")
   								.append(nonterminal.getKey().getKey())
   								.append("\"));\n");

   					builder.append("\n\t\t//** nonterminals products implementation\n\n");

   					//** nonterminals

   					for (Pair<Pair<String, Boolean>, ArrayList<ArrayList<Pair<String, Boolean>>>> nonterminal : nonterminals) {
   						if (nonterminal.getKey().getValue())
   							continue;

   						builder
   							.append("\t\tBNF.getNonterminal(_")
   							.append(nonterminal.getKey().getKey())
   							.append("_).setElements(new Element[][] {\n");

   						for (ArrayList<Pair<String, Boolean>> prod : nonterminal.getValue()) {
   							builder.append("\t\t\tnew Element[] {\n");

   							for (Pair<String, Boolean> elem : prod)
   								if (elem.getValue() == Boolean.TRUE)
   									builder
   										.append("\t\t\t\tnew BNF_NT(_")
   										.append(elem.getKey())
   										.append("_),\n");
   								else /* null, not false */
   									builder
   										.append("\t\t\t\tnew BNF_SRule(tokens -> () -> ")
   										.append(semanticRules.get(elem.getKey()))
   										.append("),\n");

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
   							.append("\t\tBNF.getLexeme(_")
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
   								else /* false, not null */
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

					if (semanticRules.containsKey("999"))
						builder
							.append("\n\t\t//** BNF finalization program fragment (semantic rule No.999)\n\n\t\t")
							.append(semanticRules.get("999"));

   					builder
   					    .append("\n\t}\n\n\tprivate ")
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

		bnf.getNonterminal(_Nonterminals_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Nonterminal_),
				new BNF_NT(_Nonterminals_)
			},
			new Element[] {

			}
		});

		bnf.getNonterminal(_Nonterminal_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_OpenSign_),
				new BNF_NT(_Dollar_),
				new BNF_NT(_Ident_),
				new BNF_SRule(tokens -> () -> nonterminals.add(new Pair<>(new Pair<>(tokens[2].getValue(), true), new ArrayList<>()))),
				new BNF_NT(_CloseSign_),
				new BNF_NT(_Assign_),
				new BNF_NT(_Productions_),
				new BNF_NT(_Dot_)
			},
			new Element[] {
				new BNF_NT(_OpenSign_),
				new BNF_NT(_Ident_),
				new BNF_SRule(tokens -> () -> nonterminals.add(new Pair<>(new Pair<>(tokens[1].getValue(), false), new ArrayList<>()))),
				new BNF_NT(_CloseSign_),
				new BNF_NT(_Assign_),
				new BNF_NT(_Productions_),
				new BNF_NT(_Dot_)
			}
		});

		bnf.getNonterminal(_Productions_).setElements(new Element[][] {
			new Element[] {
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().add(new ArrayList<>())),
				new BNF_NT(_Production_),
				new BNF_NT(_OptProductions_)
			}
		});

		bnf.getNonterminal(_OptProductions_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Or_),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().add(new ArrayList<>())),
				new BNF_NT(_Production_),
				new BNF_NT(_OptProductions_)
			},
			new Element[] {

			}
		});

		bnf.getNonterminal(_Production_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_OpenSign_),
				new BNF_NT(_Ident_),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().get(nonterminals.get(nonterminals.size() - 1).getValue().size() - 1).add(new Pair<>(tokens[1].getValue(), true))),
				new BNF_NT(_CloseSign_),
				new BNF_NT(_Production_)
			},
			new Element[] {
				new BNF_NT(_String_),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().get(nonterminals.get(nonterminals.size() - 1).getValue().size() - 1).add(new Pair<>(Methods.getEscapedString(Methods.getUnescapedString(tokens[0].getValue().substring(1, tokens[0].getValue().length() - 1))), false))),
				new BNF_NT(_Production_)
			},
			new Element[] {
				new BNF_NT(_OpenBrace_),
				new BNF_NT(_Number_),
				new BNF_SRule(tokens -> () -> nonterminals.get(nonterminals.size() - 1).getValue().get(nonterminals.get(nonterminals.size() - 1).getValue().size() - 1).add(new Pair<>(tokens[1].getValue(), null))),
				new BNF_NT(_CloseBrace_),
				new BNF_NT(_Production_)
			},
			new Element[] {

			}
		});

		bnf.getNonterminal(_Rules_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Rule_),
				new BNF_NT(_Rules_)
			},
			new Element[] {

			}
		});

		bnf.getNonterminal(_Rule_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Number_),
				new BNF_NT(_RuleSymbols_),
				new BNF_SRule(tokens -> () -> semanticRules.put(tokens[0].getValue(), tokens[1].getValue().substring(1, tokens[1].getValue().length() - 1)))
			}
		});

		bnf.getLexeme(_Ident_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Letter_),
				new BNF_NT(_OptIdent_)
			},
			new Element[] {
				new BNF_String("_"),
				new BNF_NT(_OptIdent_)
			}
		});

		bnf.getLexeme(_Number_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Digit_),
				new BNF_NT(_OptNumber_)
			}
		});

		bnf.getLexeme(_String_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("\""),
				new BNF_NT(_OptString_),
				new BNF_String("\"")
			}
		});

		bnf.getLexeme(_RuleSymbols_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("@"),
				new BNF_NT(_OptRuleSymbols_),
				new BNF_String("@")
			}
		});

		bnf.getLexeme(_Dollar_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("$")
			}
		});

		bnf.getLexeme(_Or_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("|")
			}
		});

		bnf.getLexeme(_OpenSign_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("<")
			}
		});

		bnf.getLexeme(_CloseSign_).setElements(new Element[][] {
			new Element[] {
				new BNF_String(">")
			}
		});

		bnf.getLexeme(_OpenBrace_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("{")
			}
		});

		bnf.getLexeme(_CloseBrace_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("}")
			}
		});

		bnf.getLexeme(_Assign_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("::=")
			}
		});

		bnf.getLexeme(_Dot_).setElements(new Element[][] {
			new Element[] {
				new BNF_String(".")
			}
		});

		bnf.getLexeme(_OptIdent_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Letter_),
				new BNF_NT(_OptIdent_)
			},
			new Element[] {
				new BNF_NT(_Digit_),
				new BNF_NT(_OptIdent_)
			},
			new Element[] {
				new BNF_String("_"),
				new BNF_NT(_OptIdent_)
			},
			new Element[] {

			}
		});

		bnf.getLexeme(_OptNumber_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Digit_),
				new BNF_NT(_OptNumber_)
			},
			new Element[] {

			}
		});

		bnf.getLexeme(_OptString_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_StringSymbol_),
				new BNF_NT(_OptString_)
			},
			new Element[] {

			}
		});

		bnf.getLexeme(_OptRuleSymbols_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_RuleSymbol_),
				new BNF_NT(_OptRuleSymbols_)
			},
			new Element[] {

			}
		});

		bnf.getLexeme(_StringSymbol_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Symbol_)
			},
			new Element[] {
				new BNF_String("\\\"")
			},
			new Element[] {
				new BNF_String("\\n")
			},
			new Element[] {
				new BNF_String("\\t")
			},
			new Element[] {
				new BNF_String("\\\\")
			},
			new Element[] {
				new BNF_String("@")
			},
			new Element[] {
				new BNF_String("`")
			}
		});

		bnf.getLexeme(_RuleSymbol_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Symbol_)
			},
			new Element[] {
				new BNF_String("\"")
			},
			new Element[] {
				new BNF_String("\n")
			},
			new Element[] {
				new BNF_String("\t")
			},
			new Element[] {
				new BNF_String("\\")
			},
			new Element[] {
				new BNF_String("`@")
			},
			new Element[] {
				new BNF_String("``")
			}
		});

		bnf.getLexeme(_Symbol_).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(_Digit_)
			},
			new Element[] {
				new BNF_NT(_Letter_)
			},
			new Element[] {
				new BNF_String(" ")
			},
			new Element[] {
				new BNF_String("_")
			},
			new Element[] {
				new BNF_String("\'")
			},
			new Element[] {
				new BNF_String("!")
			},
			new Element[] {
				new BNF_String("?")
			},
			new Element[] {
				new BNF_String("-")
			},
			new Element[] {
				new BNF_String("+")
			},
			new Element[] {
				new BNF_String("=")
			},
			new Element[] {
				new BNF_String("/")
			},
			new Element[] {
				new BNF_String(",")
			},
			new Element[] {
				new BNF_String("{")
			},
			new Element[] {
				new BNF_String("}")
			},
			new Element[] {
				new BNF_String("[")
			},
			new Element[] {
				new BNF_String("]")
			},
			new Element[] {
				new BNF_String("(")
			},
			new Element[] {
				new BNF_String(")")
			},
			new Element[] {
				new BNF_String("*")
			},
			new Element[] {
				new BNF_String("&")
			},
			new Element[] {
				new BNF_String("^")
			},
			new Element[] {
				new BNF_String("%")
			},
			new Element[] {
				new BNF_String("#")
			},
			new Element[] {
				new BNF_String("$")
			},
			new Element[] {
				new BNF_String(":")
			},
			new Element[] {
				new BNF_String(";")
			},
			new Element[] {
				new BNF_String(">")
			},
			new Element[] {
				new BNF_String("~")
			},
			new Element[] {
				new BNF_String(".")
			},
			new Element[] {
				new BNF_String("<")
			},
			new Element[] {
				new BNF_String("|")
			}
		});

		bnf.getLexeme(_Letter_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("a")
			},
			new Element[] {
				new BNF_String("b")
			},
			new Element[] {
				new BNF_String("c")
			},
			new Element[] {
				new BNF_String("d")
			},
			new Element[] {
				new BNF_String("e")
			},
			new Element[] {
				new BNF_String("f")
			},
			new Element[] {
				new BNF_String("g")
			},
			new Element[] {
				new BNF_String("h")
			},
			new Element[] {
				new BNF_String("i")
			},
			new Element[] {
				new BNF_String("j")
			},
			new Element[] {
				new BNF_String("k")
			},
			new Element[] {
				new BNF_String("l")
			},
			new Element[] {
				new BNF_String("m")
			},
			new Element[] {
				new BNF_String("n")
			},
			new Element[] {
				new BNF_String("o")
			},
			new Element[] {
				new BNF_String("p")
			},
			new Element[] {
				new BNF_String("q")
			},
			new Element[] {
				new BNF_String("r")
			},
			new Element[] {
				new BNF_String("s")
			},
			new Element[] {
				new BNF_String("t")
			},
			new Element[] {
				new BNF_String("u")
			},
			new Element[] {
				new BNF_String("v")
			},
			new Element[] {
				new BNF_String("w")
			},
			new Element[] {
				new BNF_String("x")
			},
			new Element[] {
				new BNF_String("y")
			},
			new Element[] {
				new BNF_String("z")
			},
			new Element[] {
				new BNF_String("A")
			},
			new Element[] {
				new BNF_String("B")
			},
			new Element[] {
				new BNF_String("C")
			},
			new Element[] {
				new BNF_String("D")
			},
			new Element[] {
				new BNF_String("E")
			},
			new Element[] {
				new BNF_String("F")
			},
			new Element[] {
				new BNF_String("G")
			},
			new Element[] {
				new BNF_String("H")
			},
			new Element[] {
				new BNF_String("I")
			},
			new Element[] {
				new BNF_String("J")
			},
			new Element[] {
				new BNF_String("K")
			},
			new Element[] {
				new BNF_String("L")
			},
			new Element[] {
				new BNF_String("M")
			},
			new Element[] {
				new BNF_String("N")
			},
			new Element[] {
				new BNF_String("O")
			},
			new Element[] {
				new BNF_String("P")
			},
			new Element[] {
				new BNF_String("Q")
			},
			new Element[] {
				new BNF_String("R")
			},
			new Element[] {
				new BNF_String("S")
			},
			new Element[] {
				new BNF_String("T")
			},
			new Element[] {
				new BNF_String("U")
			},
			new Element[] {
				new BNF_String("V")
			},
			new Element[] {
				new BNF_String("W")
			},
			new Element[] {
				new BNF_String("X")
			},
			new Element[] {
				new BNF_String("Y")
			},
			new Element[] {
				new BNF_String("Z")
			}
		});

		bnf.getLexeme(_Digit_).setElements(new Element[][] {
			new Element[] {
				new BNF_String("0")
			},
			new Element[] {
				new BNF_String("1")
			},
			new Element[] {
				new BNF_String("2")
			},
			new Element[] {
				new BNF_String("3")
			},
			new Element[] {
				new BNF_String("4")
			},
			new Element[] {
				new BNF_String("5")
			},
			new Element[] {
				new BNF_String("6")
			},
			new Element[] {
				new BNF_String("7")
			},
			new Element[] {
				new BNF_String("8")
			},
			new Element[] {
				new BNF_String("9")
			}
		});

		return bnf;
	}

	private BNF_Generator() {}
}
