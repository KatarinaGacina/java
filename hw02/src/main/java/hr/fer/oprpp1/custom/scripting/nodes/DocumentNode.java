package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class that represents the entire document, the first node.
 * @author Katarina Gacina
 */
public class DocumentNode extends Node {
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentNode)) throw new UnsupportedOperationException();
		
		int size1 = this.numberOfChildren();
		int size2 = ((DocumentNode) obj).numberOfChildren();
		
		if (size1 != size2) return false;
		
		for (int i = 0; i < size1; i++) {
			if (!(this.getChild(i).equals(((DocumentNode) obj).getChild(i)))) {
				return false;
			}
		}
		
		return true;
	}
}
