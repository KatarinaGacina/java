package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Interface that represents SingleDocumentModel listener.
 * @author Katarina Gacina
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Document is modified.
	 * @param model specified SingleDocumentModel
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Document file path is updated.
	 * @param model specified SingleDocumentModel
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
