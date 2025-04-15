package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Interface that represents MultipleDocumentModel listener. 
 * @author Katarina Gaćina
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Change of current document.
	 * @param previousModel previous document, can be null
	 * @param currentModel current document, can be null
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * New document is added.
	 * @param model new document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Document is removed.
	 * @param model removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
