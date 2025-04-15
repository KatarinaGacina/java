package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptLexerException;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Class that represents smart script parser.
 * @author Katarina Gacina
 */
public class SmartScriptParser {
	/**
	 * script lexer used for parsing the document
	 */
	private ScriptLexer scriptLexer;
	/**
	 * stack 
	 */
	private ObjectStack stackTree;
	
	/**
	 * an entire document representation
	 */
	private DocumentNode documentNode;
	
	/**
	 * Constructor accepts the string which contains the document body and parses through the text.
	 * @param text string which contains the document body
	 * @throws SmartScriptException() if it is given a null reference
	 */
	public SmartScriptParser(String text) {
		if (text == null) throw new SmartScriptParserException();
		
		this.scriptLexer = new ScriptLexer(text);
		
		this.stackTree = new ObjectStack();
		this.documentNode = new DocumentNode();
		
		this.stackTree.push(this.documentNode);
		
		parseText();
	}
	
	/**
	 * Function returns DocumentNode which is an entire document representation.
	 * @return DocumentNode an entire document representation
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}
	
	/**
	 * Function used for the document parsing.
	 * It uses lexer for the production of token.
	 * @throws SmartScriptParserException() if exception occurs due to the incorrect text format
	 */
	private void parseText() {
		
		try {
			ScriptToken token = this.scriptLexer.nextScriptToken();

			while (token.getType() != ScriptTokenType.EOF) { // EOF means we reached the end of the document
				
				if (token.getType() == ScriptTokenType.CHANGE) { // CHANGE lexer changed state, this token is of no use to parser so parser continues
					token = this.scriptLexer.nextScriptToken();
	
				} else if (token.getType() == ScriptTokenType.TEXT) { // TEXT parser has to create text node 
					Node newNode = new TextNode(token.getValue().asText());
					addChildNodeStack(newNode);  // add it as a child to a previous node on the stack
					
					token = this.scriptLexer.nextScriptToken();
					
				} else if (token.getType() == ScriptTokenType.FOR) { // FOR parser creates for loop node of specified components
					token = this.scriptLexer.nextScriptToken();
					
					ElementVariable variable;
					if (token.getValue() instanceof ElementVariable) {
						variable = (ElementVariable) token.getValue();
					} else {
						throw new SmartScriptParserException();
					}
					
					token = this.scriptLexer.nextScriptToken();
					
					Element startExpression;
					if (token.getType() == ScriptTokenType.FORELEMENT) {
						startExpression = token.getValue();
					} else {
						throw new SmartScriptParserException();
					} 
					
					token = this.scriptLexer.nextScriptToken();
					
					Element endExpression;
					if (token.getType() == ScriptTokenType.FORELEMENT) {
						endExpression = token.getValue();
					} else {
						throw new SmartScriptParserException();
					}
					
					token = this.scriptLexer.nextScriptToken();
					
					Element stepExpression;
					if (token.getType() == ScriptTokenType.FORELEMENT) {
						stepExpression = token.getValue();
					} else {
						stepExpression = null;
					}
					
					ForLoopNode newNode = new ForLoopNode(variable, startExpression,
							endExpression, stepExpression);

					addChildNodeStack(newNode); // for loop node is added as a child to the previous node on the stack
					
					this.stackTree.push(newNode); // since for loop node can contain children node it is pushed on the stack
					
					if (token.getType() == ScriptTokenType.FORELEMENT) { // since stepExpression might not exist, we need to check if the last read token was FORELEMENT, so that we do not skip any token 
						token = this.scriptLexer.nextScriptToken();
					}
					
				} else if (token.getType() == ScriptTokenType.END) { // END signalises for loop has ended, therefore it has to be removed from the stack
					this.stackTree.pop();
					if (this.stackTree.isEmpty()) throw new SmartScriptParserException();
					
					token = this.scriptLexer.nextScriptToken();
					
				} else if (token.getType() == ScriptTokenType.ECHO) { // ECHO parser creates echo node with containing elements
					token = this.scriptLexer.nextScriptToken();
					
					ObjectStack echoElements = new ObjectStack();
					while (token.getType() == ScriptTokenType.ECHOELEMENT) {
						echoElements.push(token.getValue());

						token = this.scriptLexer.nextScriptToken();
					}
					
					Element[] elements = new Element[echoElements.size()];
	
					int j = 0;
					while (!echoElements.isEmpty()) {
						elements[j] = (Element) echoElements.pop();
						
						j++;
					}
					
					EchoNode newNode = new EchoNode(elements);
					addChildNodeStack(newNode); // add it as a child to a previous node on the stack
			
				} else {
					throw new SmartScriptParserException(); 
				}
			}
					
			//here if we are at the end of the file
			
			this.stackTree.pop(); // pop node from the stack
			if (!this.stackTree.isEmpty()) throw new SmartScriptParserException(); // if parsing was successful, stack should be empty
		
		} catch (ScriptLexerException ex) {
			throw new SmartScriptParserException();
		}
		
	}
	
	/**
	 * Function takes the node from the top of the stack, adds new node as its child and pushed the node back on the stack.
	 * @param newNode node that was added as child to the node at the top of the stack
	 */
	private void addChildNodeStack(Node newNode) {
		
		Node parentNode = (Node) this.stackTree.pop();
		parentNode.addChildNode(newNode);

		this.stackTree.push(parentNode);
	}
	
}
