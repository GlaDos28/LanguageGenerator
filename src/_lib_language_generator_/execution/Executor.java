package _lib_language_generator_.execution;

import _lib_language_generator_.structures.bnf.BNF;
import _lib_language_generator_.structures.bnf.productions.BNF_BasicProduction;
import _lib_language_generator_.structures.bnf.productions.BNF_LexemeProduction;
import _lib_language_generator_.structures.bnf.productions.misc.elements.*;
import _lib_language_generator_.structures.misc.Pos;
import _lib_language_generator_.structures.token.Token;
import _lib_language_generator_.util.exceptions.LexerException;
import _lib_language_generator_.util.exceptions.ParserException;

import java.util.ArrayList;

/**
 * Executor that finally executes semantic rules that should generate something as output.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class Executor {
	private final BNF              bnf;
	private final String           text;
	private       int              textPos;
	private       Pos              textLCPos;
	private final ArrayList<Token> tokens;
	private       int              tokenPos;

	public Executor(BNF bnf, String text) {
		if (bnf == null || text == null)
			throw new RuntimeException("invalid input { bnf: " + bnf + ", text: " + text + " }");

		this.bnf       = bnf;
		this.text      = text;
		this.textPos   = 0;
		this.textLCPos = new Pos(1, 1);
		this.tokens    = new ArrayList<>();
		this.tokenPos  = -1;
	}

	//** lexical analyzer

	private boolean isWhiteSpace(char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n');
	}

	private void readLexeme(BNF_LexemeProduction lexeme) throws LexerException {
		Element[][] prodsElems = lexeme.getProductionsElements();
		boolean     isRead     = false;

		for (int i = 0; !isRead && i < prodsElems.length; i++) {
			int savedTextPos   = this.textPos;
			Pos savedTextLCPos = this.textLCPos;

			try {
				for (int j = 0; j < prodsElems[i].length; j++) {
					Element elem = prodsElems[i][j];

					if (elem instanceof BNF_String) {
						String elemStr = ((BNF_String) elem).get();

						if (!this.text.startsWith(elemStr, this.textPos))
							throw new LexerException(); /* the exception will always be intercepted in the try-catch block */

						this.textPos += elemStr.length();
						this.textLCPos = new Pos(this.textLCPos.getLine(), this.textLCPos.getColumn() + elemStr.length()); /* consider elemStr does not contain newline characters */
					}
					else if (elem instanceof BNF_NT)
						readLexeme(bnf.getLexeme((BNF_NT) elem));
					else
						throw new RuntimeException("invalid element type: " + elem.getType());
				}

				isRead = true;
			}
			catch (LexerException ex) {
				this.textPos   = savedTextPos;
				this.textLCPos = savedTextLCPos;

				if (i == prodsElems.length - 1)
					throw new LexerException(); /* the exception will always be intercepted in the try-catch block in nextToken() */
			}
		}
	}

	private Token nextToken() throws LexerException {
		this.tokenPos++;

		if (this.tokenPos < this.tokens.size())
			return this.tokens.get(this.tokenPos);

		//**

		while (this.textPos < this.text.length() && this.isWhiteSpace(this.text.charAt(this.textPos))) {
			if (this.text.charAt(this.textPos) == '\n')
				this.textLCPos = new Pos(this.textLCPos.getLine() + 1, 1);
			else
				this.textLCPos = new Pos(this.textLCPos.getLine(), this.textLCPos.getColumn() + 1);

			this.textPos++;
		}

		if (this.textPos >= this.text.length()) {
			if (this.tokens.isEmpty() || this.tokens.get(this.tokens.size() - 1) != null)
				this.tokens.add(null);

			return this.tokens.get(this.tokenPos);
		}

		int                  i              = 0;
		boolean              isRead         = false;
		BNF_LexemeProduction lexeme         = null;
		int                  savedTextPos   = 0;
		Pos                  savedTextLCPos = null;

		for (; i < this.bnf.getLexemeNum();) {
			try {
				while (i < this.bnf.getLexemeNum() && !this.bnf.getLexemeFromList(i).getFirst(this.bnf).contains(new BNF_Char(this.text.charAt(this.textPos))))
					i++;

				if (i >= this.bnf.getLexemeNum())
					throw new LexerException(this.text.charAt(this.textPos), this.textLCPos);

				lexeme         = this.bnf.getLexemeFromList(i);
				savedTextPos   = this.textPos;
				savedTextLCPos = this.textLCPos;

				readLexeme(lexeme);

				isRead = true;
				break;
			}
			catch (LexerException ex) {
				i++;
			}
		}

		if (!isRead)
			throw new LexerException("cannot read symbols from position " + this.textLCPos, this.text.charAt(this.textPos), this.textLCPos);

		//System.out.println("DEBUG: " + lexeme.getName() + " | " + this.text.substring(savedTextPos, this.textPos)); /* for debug */

		this.tokens.add(new Token(savedTextLCPos, lexeme.getName(), this.text.substring(savedTextPos, this.textPos)));
		return this.tokens.get(this.tokenPos);
	}

	//** syntactical analyzer

	private ArrayList<Runnable> parse(BNF_BasicProduction nonterminal) throws LexerException, ParserException {
		Element[][]         prodsElems   = nonterminal.getProductionsElements();
		boolean             isRead       = false;
		ArrayList<Runnable> rules        = new ArrayList<>();
		ArrayList<Token>    pickedTokens = new ArrayList<>();

		for (int i = 0; !isRead && i < prodsElems.length; i++) {
			int savedTokenPos = this.tokenPos;

			try {
				for (int j = 0; j < prodsElems[i].length; j++) {
					Element elem = prodsElems[i][j];

					if (elem instanceof BNF_NT) {
						BNF_NT nt = (BNF_NT) elem;

						BNF_BasicProduction elementProds = bnf.getNonterminal(nt);

						if (elementProds instanceof BNF_LexemeProduction) {
							Token token = this.nextToken();

							if (token == null || !token.getType().equals(elementProds.getName()))
								throw new ParserException(); /* the exception will always be intercepted in the try-catch block */

							pickedTokens.add(token);
						}
						else /* BNF_BasicProduction */
							rules.addAll(parse(bnf.getNonterminal(nt)));
					}
					else if (elem instanceof BNF_SRule)
						rules.add(((BNF_SRule) elem).extractRunnable(pickedTokens));
					else
						throw new RuntimeException("invalid element type: " + elem.getType());
				}

				isRead = true;
			}
			catch (ParserException ex) {
				this.tokenPos = savedTokenPos;
				pickedTokens.clear();
				rules.clear();

				if (i == prodsElems.length - 1) {
					Token token = this.tokens.get(this.tokenPos + 1);
					throw new ParserException("cannot parse text from position " + ((token == null) ? this.textLCPos : token.getPos()), token);
				}
			}
		}

		return rules;
	}

	//**

	public void execute() throws LexerException, ParserException {
		ArrayList<Runnable> semanticRules = this.parse(this.bnf.getFirstNonterminal());

		Token token = this.nextToken();

		if (token != null)
			throw new ParserException("unexpected code starting from " + token.getPos(), token);

		for (Runnable semanticRule : semanticRules)
			semanticRule.run();
	}
}
