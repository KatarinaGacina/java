package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

/**
 * Class that represents a model of single document, having information about file path from which document was loaded (can be null for new document), document modification status 
 * and reference to Swing component which is used for editing (each document has its own editor component).
 * @author Katarina Gaćina
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * text editor
	 */
	private JTextArea textArea;
	
	/**
	 * modification status
	 */
	private boolean modified = false;
	
	/**
	 * file path
	 */
	private Path path;
	
	/**
	 * SingleDocumentListener list
	 */
	private List<SingleDocumentListener> listenerList = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param filePath file path
	 * @param textContent file content
	 */
	public DefaultSingleDocumentModel(String filePath, String textContent) {
		if (filePath == null) {
			this.path = null;
		} else {
			this.path = Path.of(filePath);
		}
		
		this.textArea = new JTextArea(textContent);
		
		for (SingleDocumentListener l : listenerList) {
			l.documentModifyStatusUpdated(this);
		}
	}
	
	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		
		for (SingleDocumentListener l : listenerList) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for (SingleDocumentListener l : listenerList) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (l != null) listenerList.add(l);		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		for (SingleDocumentListener listener : listenerList) {
			if (listener.equals(l)) {
				listenerList.remove(listener);
			}
		}
	}

}
