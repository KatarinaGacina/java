package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;

/**
 * Base class used for representation of document structure.
 * @author Katarina Gacina
 */
public class Node {
	/**
	 * collection of node children
	 */
	private ArrayIndexedCollection nodeCollection;
	
	/**
	 * Function adds the given child to the node child collection.
	 */
	public void addChildNode(Node child) { 
		if (this.nodeCollection == null) {
			this.nodeCollection = new ArrayIndexedCollection();
		}
		
		this.nodeCollection.add(child);
	}
	
	/**
	 * Function returns a number of node (direct) children.
	 * @return int number of node (direct) children
	 */
	public int numberOfChildren() {
		if (this.nodeCollection == null) return 0;
		
		return this.nodeCollection.size();
	}
	
	/**
	 * Function returns selected child from node child collection.
	 * @param index position of child node 
	 * @return Node selected child node
	 * @throws IndexOutOfBoundsException if node does not have any children 
	 * or the given index is less than 0 or greater than node child collection size - 1
	 */
	public Node getChild(int index) {
		if (this.nodeCollection == null) throw new IndexOutOfBoundsException();
		if (index < 0 || index > this.nodeCollection.size() - 1) throw new IndexOutOfBoundsException(); 
		
		return (Node) this.nodeCollection.get(index);
	}
	
	/**
	 * Function checks if two nodes are structurally the same.
	 * @param obj object with which the node is compared
	 * @return true if two nodes are structurally the same, false if they are not
	 * @throws UnsupportedOperationException if the given object is not an instance of the class Node
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node)) throw new UnsupportedOperationException();
		
		int size1 = this.numberOfChildren();
		int size2 = ((Node) obj).numberOfChildren();
		
		if (size1 != size2) return false;
		
		for (int i = 0; i < size1; i++) {
			if (!(this.getChild(i).equals(((Node) obj).getChild(i)))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Function returns string representation of the node.
	 * @return String node string representation
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = numberOfChildren();
		
		for (int i = 0; i < size; i++) {
		    Node child = this.getChild(i);
		    
		    if (child instanceof ForLoopNode) {
		    	sb.append("{$ FOR ");
		    	
		    	sb.append(((ForLoopNode) child).getVariable().asText());
		    	sb.append(" ");
		    	
		    	Element e =((ForLoopNode) child).getStartExpression();
		    	sb = checkElement(e, sb);
		    	e = ((ForLoopNode) child).getEndExpression();
		    	sb = checkElement(e, sb);
		    	
		    	if (((ForLoopNode) child).getStepExpression() != null) {
		    		e = ((ForLoopNode) child).getStepExpression();
			    	sb = checkElement(e, sb);
		    	}
		    	
		    	sb.append("$}");
		    	
		    	sb.append(child.toString());
		    	
		    	sb.append("{$END$}");
		    } else if (child instanceof TextNode) {
		    	sb.append(((TextNode) child).getText());

		    } else if (child instanceof EchoNode) {
		    	sb.append("{$ = ");
		    	
		    	Element[] elementi = ((EchoNode) child).getElements();
		    	
		    	for (Element e : elementi) {
		    		sb = checkElement(e, sb);
		    	}
		    	
		    	sb.append("$}");
		    }
		}
		
		return sb.toString();
	}
	
	/**
	 * Function adds element value and adds appropriate characters if needed.
	 * and in that case adds appropriate characters to StringBuilder.
	 * @param e element which is checked
	 * @param sb StringBuilder to which Element text representation is added
	 * @return StringBuilder updated node text representation
	 */
	private StringBuilder checkElement(Element e, StringBuilder sb) {
		if (e instanceof ElementString) {
    		sb.append("\"");
    		sb.append(e.asText());
    		sb.append("\"");
    	} else if (e instanceof ElementFunction) {
    		sb.append("@");
    		sb.append(e.asText());
		} else {
    		sb.append(e.asText());
    	}
		
    	sb.append(" ");
    	
    	return sb;
	} 
	
}
