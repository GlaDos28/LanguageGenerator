package _lib_language_generator_.launching;

import _lib_language_generator_.execution.Executor;
import _lib_language_generator_.structures.bnf.BNF;

import _lib_language_generator_.util.exceptions.LexerException;
import _lib_language_generator_.util.exceptions.ParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Launcher.
 *
 * @author GlaDos
 * @since 08.07.17
 */
public final class Launcher {
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Usage: <BNF path> <out path> <class name> <package name>");
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
			StringBuilder builder = new StringBuilder();

			while (reader.ready())
				builder.append(reader.readLine()).append('\n');

			BNF bnf = BNF_Generator.getBNF(args[1], args[2], args[3]);
			Launcher.compile(builder.toString(), bnf);
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
			return;
		}
	}

	public static void compile(String text, BNF bnf) {
		Executor executor = new Executor(bnf, text);

		System.out.println("Trying to compile text");

		try {
			executor.execute();
			System.out.println("Success!");
		}
		catch (LexerException | ParserException ex) {
			System.out.println("Fail!");
			System.out.println(ex.getMessage());
		}
	}

	private Launcher() {}
}
