package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * Class that represents a model capable of holding zero, one or more documents, where each document and having a concept of current document.
 * @author Katarina Gacina
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	/**
	 * list of documents
	 */
	private List<SingleDocumentModel> components = new ArrayList<>();
	
	/**
	 * current document
	 */
	private SingleDocumentModel currentComponent = null;
	
	/**
	 * MultipleDocumentListener list
	 */
	private List<MultipleDocumentListener> listenerList = new ArrayList<>();
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return components.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		currentComponent = new DefaultSingleDocumentModel(null, "");
		components.add(currentComponent);
		
		for (MultipleDocumentListener l : listenerList) {
			l.documentAdded(currentComponent);
		}
		
		return currentComponent;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentComponent;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		byte[] okteti;
		
		String tekst;
		try {
			okteti = Files.readAllBytes(path);
			tekst = new String(okteti, StandardCharsets.UTF_8);
			
		} catch(Exception ex) {
			return null;
		}	
		
		currentComponent = new DefaultSingleDocumentModel(path.toString(), tekst);
		components.add(currentComponent);
		
		for (MultipleDocumentListener l : listenerList) {
			l.documentAdded(currentComponent);
		}
		
		return currentComponent;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		model.setFilePath(newPath);		
		
		for (MultipleDocumentListener l : listenerList) {
			l.currentDocumentChanged(currentComponent, model);
		}
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		components.remove(model);
		
		for (MultipleDocumentListener l : listenerList) {
			l.documentRemoved(model);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l != null) listenerList.add(l);		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		for (MultipleDocumentListener listener : listenerList) {
			if (listener.equals(l)) {
				listenerList.remove(listener);
			}
		}
	}

	@Override
	public int getNumberOfDocuments() {
		return components.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return components.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		for (SingleDocumentModel doc : components) {
			if (doc.getFilePath().equals(path)) {
				return doc;
			}
		}
		
		return null;
	}
	
	/**
	 * Function sets current document and notifies all registered listeners that current document has changed.
	 * @param document current document
	 */
	public void setCurrentDocument(SingleDocumentModel document) {
		for (MultipleDocumentListener l : listenerList) {
			l.currentDocumentChanged(currentComponent, document);
		}
		
		this.currentComponent = document;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		if (components.contains(doc)) {
			return components.indexOf(doc);
		} else {
			return -1;
		}
	}

}
