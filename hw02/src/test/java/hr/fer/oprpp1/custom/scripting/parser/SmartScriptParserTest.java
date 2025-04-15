package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTest {

	@Test
	public void testNullText() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
	}
	
	@Test
	public void testPrimjer1() {
		String text = readExample(1);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(node.numberOfChildren(), 1);
	}
	

	@Test
	public void testPrimjer2() {
		String text = readExample(2);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(node.numberOfChildren(), 1);
	}

	@Test
	public void testPrimjer3() {
		String text = readExample(3);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(1, node.numberOfChildren());
	}

	@Test
	public void testPrimjer4() {
		String text = readExample(4);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer5() {
		String text = readExample(5);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer6() {
		String text = readExample(6);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(3, node.numberOfChildren());
		
	}

	@Test
	public void testPrimjer7() {
		String text = readExample(7);
		
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(3, node.numberOfChildren());
		
	}

	@Test
	public void testPrimjer8() {
		String text = readExample(8);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer9() {
		String text = readExample(9);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testPrimjer10() {
		String text = readExample(10);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();

		assertEquals(1, node.numberOfChildren());
	}
	
	@Test
	public void testPrimjer11() {
		String text = readExample(11);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();

		assertEquals(4, node.numberOfChildren());
	}
	
	@Test
	public void testPrimjer12() {
		String text = readExample(12);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();

		assertEquals(4, node.numberOfChildren());
	}
	
	@Test
	public void testPrimjer13() {
		String text = readExample(13);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testPrimjer14() {
		String text = readExample(14);

		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode node = parser.getDocumentNode();

		assertEquals(5, node.numberOfChildren());
	}
	
	@Test
	public void testPrimjer15() {
		String text = readExample(15);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    
			byte[] data = is.readAllBytes();
		    
			String text = new String(data, StandardCharsets.UTF_8);
		    
			return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
}
