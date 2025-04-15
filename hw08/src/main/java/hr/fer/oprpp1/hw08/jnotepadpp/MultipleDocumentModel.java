package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JComponent;

/**
 * Interface  that represents a model capable of holding zero, one or more documents, where each document and having a concept of current document.
 * @author Katarina Gaćina
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Function that returns the graphical component which is responsible for displaying the entire MultipleDocumentModel‘s user interface. 
	 * @return this
	 */
	JComponent getVisualComponent();
	
	/**
	 * Function that creates new document.
	 * @return new document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Function that returns current document.
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Function that loads the existing document.
	 * @param path file path, must not be null
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Function saves document. If newPath is null, document is saved using the path associated with the document.
	 * @param model document that needs to be saved
	 * @param newPath new file path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Function removes specified document (does not check modification status or ask any questions).
	 * @param model specified document
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Function adds MultipleDocumentListener.
	 * @param l MultipleDocumentListener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Function removes specified document listener.
	 * @param l specified MultipleDocumentListener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Function returns number of documents in the MultipleDocumentModel.
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Function returns document from the specified index.
	 * @param index specified index of the document
	 * @return SingleDocumentModel document
	 */
	SingleDocumentModel getDocument(int index);
	
	/**
	 * Function return SingleDocumentModel for the specified path or null, if no such model exists.
	 * @param path specified path
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel findForPath(Path path); 
	
	/**
	 * Function return index of the specified SingleDocumentModel or -1 if not present.
	 * @param doc specified SingleDocumentModel
	 * @return index of the specified SingleDocumentModel or -1 if not present
	 */
	int getIndexOfDocument(SingleDocumentModel doc); 
}
