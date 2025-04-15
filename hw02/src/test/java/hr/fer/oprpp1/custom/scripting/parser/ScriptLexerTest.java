package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptLexerException;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptTokenType;

public class ScriptLexerTest {

	@Test
	public void testNotNull() {
		ScriptLexer lexer = new ScriptLexer("");
		
		assertNotNull(lexer.nextScriptToken());
	}

	@Test
	public void testNullInput() {
		assertThrows(ScriptLexerException.class, () -> new ScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		ScriptLexer lexer = new ScriptLexer("");
		
		assertEquals(ScriptTokenType.EOF, lexer.nextScriptToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		ScriptLexer lexer = new ScriptLexer("");
		
		ScriptToken token = lexer.nextScriptToken();
		assertEquals(token, lexer.getScriptToken());
		assertEquals(token, lexer.getScriptToken());
	}
	
	@Test
	public void testRadAfterEOF() {
		ScriptLexer lexer = new ScriptLexer("");

		lexer.nextScriptToken();

		assertThrows(ScriptLexerException.class, () -> lexer.nextScriptToken());
	}
	
	@Test
	public void testNoActualContent() {
		ScriptLexer lexer = new ScriptLexer("   \r\n\t    ");
		
		assertEquals(ScriptTokenType.TEXT, lexer.nextScriptToken().getType());
	}
	
	@Test
	public void testInput1() {
		ScriptLexer lexer = new ScriptLexer("  test for \\{$ {$ = i 2 + \"5.25\" \"\n \\\"Long\\\" \" $} { blue } bird ");

		ScriptToken correctData[] = {
			new ScriptToken(ScriptTokenType.TEXT, new ElementString("  test for {$ ")),
			new ScriptToken(ScriptTokenType.ECHO, new ElementString("=")),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementVariable("i")),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementConstantInteger(2)),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementOperator("+")),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementString("5.25")),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementString("\n \"Long\" ")),
			new ScriptToken(ScriptTokenType.CHANGE, new ElementString("$}")),
			new ScriptToken(ScriptTokenType.TEXT, new ElementString(" { blue } bird ")),
			new ScriptToken(ScriptTokenType.EOF, new Element())
		};

		checkScriptTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInput2() {
		ScriptLexer lexer = new ScriptLexer("This is sample text.\r\n{$ FOR i 1 -10 1.2 $}\r\nThis is {$= @i $}-th time this message is generated 2 * 10.\r\n{$END$}");

		ScriptToken correctData[] = {
			new ScriptToken(ScriptTokenType.TEXT, new ElementString("This is sample text.\r\n")),
			new ScriptToken(ScriptTokenType.FOR, new ElementString("FOR")),
			new ScriptToken(ScriptTokenType.FORELEMENT, new ElementVariable("i")),
			new ScriptToken(ScriptTokenType.FORELEMENT, new ElementConstantInteger(1)),
			new ScriptToken(ScriptTokenType.FORELEMENT, new ElementConstantInteger(-10)),
			new ScriptToken(ScriptTokenType.FORELEMENT, new ElementConstantDouble(1.2)),
			new ScriptToken(ScriptTokenType.CHANGE, new ElementString("$}")),
			new ScriptToken(ScriptTokenType.TEXT, new ElementString("\r\nThis is ")),
			new ScriptToken(ScriptTokenType.ECHO, new ElementString("=")),
			new ScriptToken(ScriptTokenType.ECHOELEMENT, new ElementFunction("i")),
			new ScriptToken(ScriptTokenType.CHANGE, new ElementString("$}")),
			new ScriptToken(ScriptTokenType.TEXT, new ElementString("-th time this message is generated 2 * 10.\r\n")),
			new ScriptToken(ScriptTokenType.END, new ElementString("END")),
			new ScriptToken(ScriptTokenType.EOF, new Element())
		};

		checkScriptTokenStream(lexer, correctData);
	}

	private void checkScriptTokenStream(ScriptLexer lexer, ScriptToken[] correctData) {
		for(ScriptToken expected : correctData) {
			ScriptToken actual = lexer.nextScriptToken();
			
			assertEquals(expected.getType(), actual.getType());
			assertEquals(expected.getValue().getClass(), actual.getValue().getClass());
			assertEquals(expected.getValue().asText(), actual.getValue().asText());
		}
		
	}
}
