package testLauncher;

import execution.Executor;
import structures.bnf.BNF;
import structures.bnf.productions.BNF_BasicProduction;
import structures.bnf.productions.BNF_LexemeProduction;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.BNF_SRule;
import structures.bnf.productions.misc.elements.BNF_String;
import structures.bnf.productions.misc.elements.Element;
import structures.token.Token;
import util.exceptions.LexerException;
import util.exceptions.ParserException;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Testing launcher.
 *
 * @author GlaDos
 * @since 08.07.17
 */
public final class Launcher {
	public static void main(String[] args) {
		String text =
			"int a = 2;\n" +
			"int b = 4;";

		BNF bnf = getBNF();

		Executor executor = new Executor(bnf, text);

		//**

		System.out.println("Try parsing text\n______________");

		try {
			executor.execute();
			System.out.println("______________\nSuccess!");
		}
		catch (LexerException | ParserException ex) {
			System.out.println("______________\nFail!");
			System.out.println(ex.getMessage());
		}
	}

	private static BNF getBNF() {
		BNF bnf = new BNF();

		int programId       = bnf.addNonterminal(new BNF_BasicProduction("Program"));
		int declarationsId  = bnf.addNonterminal(new BNF_BasicProduction("Declarations"));
		int declarationId   = bnf.addNonterminal(new BNF_BasicProduction("Declaration"));

		int typeId          = bnf.addLexeme(new BNF_LexemeProduction("Type"));
		int identifierId    = bnf.addLexeme(new BNF_LexemeProduction("Identifier"));
		int numberId        = bnf.addLexeme(new BNF_LexemeProduction("Number"));
		int optIdentifierId = bnf.addLexeme(new BNF_LexemeProduction("OptIdentifier"));
		int optNumberId     = bnf.addLexeme(new BNF_LexemeProduction("OptNumber"));
		int assignId        = bnf.addLexeme(new BNF_LexemeProduction("Assign"));
		int semicolonId     = bnf.addLexeme(new BNF_LexemeProduction("Semicolon"));
		int letterId        = bnf.addLexeme(new BNF_LexemeProduction("Letter"));
		int digitId         = bnf.addLexeme(new BNF_LexemeProduction("Digit"));

		//** nonterminals

		bnf.getNonterminal(programId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(declarationsId)
			}
		});

		bnf.getNonterminal(declarationsId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(declarationId),
				new BNF_NT(declarationsId)
			},
			new Element[] {
				new BNF_NT(-1)
			}
		});

		bnf.getNonterminal(declarationId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(typeId),
				new BNF_NT(identifierId),
				new BNF_NT(assignId),
				new BNF_NT(numberId),
				new BNF_NT(semicolonId),
				new BNF_SRule(tokens -> () -> System.out.println("Hello from " + Arrays.asList(Stream.of(tokens).map(Token::getValue).toArray()) + "!"))
			}
		});

		//** lexemes

		bnf.getLexeme(typeId).setElements(new Element[][] {
			new Element[] {
				new BNF_String("int")
			},
			new Element[] {
				new BNF_String("float")
			},
			new Element[] {
				new BNF_String("bool")
			}
		});

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
			new Element[] {
				new BNF_String("")
			}
		});

		bnf.getLexeme(numberId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(digitId),
				new BNF_NT(optNumberId)
			}
		});

		bnf.getLexeme(optNumberId).setElements(new Element[][] {
			new Element[] {
				new BNF_NT(digitId),
				new BNF_NT(optNumberId)
			},
			new Element[] {
				new BNF_String("")
			}
		});

		bnf.getLexeme(assignId).setElements(new Element[][] {
			new Element[] { new BNF_String("=") }
		});

		bnf.getLexeme(semicolonId).setElements(new Element[][] {
			new Element[] { new BNF_String(";") }
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
			new Element[] { new BNF_String("z") }
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
}
