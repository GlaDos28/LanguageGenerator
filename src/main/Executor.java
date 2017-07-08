package main;

import structures.bnf.BNF;
import structures.bnf.productions.BNF_LexemeProduction;
import structures.bnf.productions.misc.elements.BNF_Char;
import structures.bnf.productions.misc.elements.BNF_NT;
import structures.bnf.productions.misc.elements.BNF_String;
import structures.bnf.productions.misc.elements.Element;
import structures.misc.Pos;
import structures.token.Token;
import util.exceptions.LexerException;

/**
 * Executor that finally executes semantic rules that should generate something as output.
 *
 * @author GlaDos
 * @since 07.07.17
 */
public final class Executor {
	private final BNF    bnf;
	private final String text;
	private       Token  curToken;
	private       int    textPos;
	private       Pos    textLCPos;

	public Executor(BNF bnf, String text) {
		this.bnf       = bnf;
		this.text      = text;
		this.curToken  = null;
		this.textPos   = 0;
		this.textLCPos = new Pos(1, 1);
	}

	private char forwardSymbol() {
		return (this.textPos + 1 < this.text.length()) ?
			this.text.charAt(this.textPos + 1) :
			' ';
	}

	private boolean isWhiteSpace(char ch) {
		return (ch == ' ') || (ch == '\t') || (ch == '\n');
	}

	private void readLexeme(BNF_LexemeProduction lexeme) throws LexerException {
		while (this.textPos < this.text.length() && this.isWhiteSpace(this.text.charAt(this.textPos))) {
			this.textPos++;

			if (this.text.charAt(this.textPos) == '\n')
				this.textLCPos = new Pos(this.textLCPos.getLine() + 1, 1);
			else
				this.textLCPos = new Pos(this.textLCPos.getLine() + 1, this.textLCPos.getColumn() + 1);
		}

		Element[][] prodsElems = lexeme.getProductionsElements();

		for (int i = 0; i < prodsElems.length; i++) {
			int savedTextPos   = this.textPos;
			Pos savedTextLCPos = this.textLCPos;

			try {
				for (int j = 0; j < prodsElems[i].length; j++) {
					Element elem = prodsElems[i][j];

					if (elem instanceof BNF_String) {
						/* TODO */
					}
					else if (elem instanceof BNF_NT)
						readLexeme(bnf.getLexeme((BNF_NT) elem));
				}
			}
			catch (LexerException ex) {
				this.textPos   = savedTextPos;
				this.textLCPos = savedTextLCPos;

				if (i == prodsElems[i].length - 1)
					throw new LexerException("cannot read symbols from position " + this.textLCPos, this.text.charAt(this.textPos), this.textLCPos);
			}
		}
	}

	private void nextToken() throws LexerException {
		while (this.textPos < this.text.length() && this.isWhiteSpace(this.text.charAt(this.textPos))) {
			this.textPos++;

			if (this.text.charAt(this.textPos) == '\n')
				this.textLCPos = new Pos(this.textLCPos.getLine() + 1, 1);
			else
				this.textLCPos = new Pos(this.textLCPos.getLine() + 1, this.textLCPos.getColumn() + 1);
		}

		if (this.textPos >= this.text.length()) {
			this.curToken = null; /* means the end of the text */
			return;
		}

		int i = 0;

		while (i < this.bnf.getLexemeNum() && !this.bnf.getLemexeFromList(i).getFirst(this.bnf).contains(new BNF_Char(this.text.charAt(this.textPos))))
			i++;

		if (i >= this.bnf.getLexemeNum())
			throw new LexerException(this.text.charAt(this.textPos), this.textLCPos);

		BNF_LexemeProduction lexeme = this.bnf.getLemexeFromList(i);
		int savedTextPos = this.textPos;

		readLexeme(lexeme);

		this.curToken = new Token(null, lexeme.getName(), this.text.substring(savedTextPos, this.textPos));
	}

	public void execute() {

	}
}
