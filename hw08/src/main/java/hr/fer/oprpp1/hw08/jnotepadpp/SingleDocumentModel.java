package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents a model of single document, having information about file path from which document was loaded (can be null for new document), document modification status
 * and reference to Swing component which is used for editing (each document has its own editor component)
 * @author Katarina Gacina
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Function returns document text component.
	 * @return JTextArea
	 */
	JTextArea getTextComponent();
	
	/**
	 * Function returns document file path. Can be null.
	 * @return document file path
	 */
	Path getFilePath();
	
	/**
	 * Function sets document file path. Path must not be null.
	 * @param path document file path
	 */
	void setFilePath(Path path);
	
	/**
	 * Function returns document modification status.
	 * @return document modification status
	 */
	boolean isModified();
	
	/**
	 * Function sets modification status to the specified value.
	 * @param modified specified modification value
	 */
	void setModified(boolean modified);
	
	/**
	 * Function adds specified SingleDocumentListener.
	 * @param l specified SingleDocumentListener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Function removes specified SingleDocumentListener.
	 * @param l sppecified SingleDocumentListener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
