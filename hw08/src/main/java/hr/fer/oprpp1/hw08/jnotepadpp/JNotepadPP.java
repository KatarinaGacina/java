package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Class that represents JNotepad application.
 * @author Katarina Gacina
 *
 */
public class JNotepadPP extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * FormLocalizationProvider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * multiple document model
	 */
	private DefaultMultipleDocumentModel mdm = new DefaultMultipleDocumentModel();
	
	/**
	 * button for statistical information
	 */
	private JButton statsButton;
	/**
	 * tool bar
	 */
	private JToolBar toolBar;
	
	/**
	 * label for displaying document length
	 */
	private JLabel len;
	/**
	 * label for displaying document caret position and number of characters in selected text
	 */
	private JLabel ln;
	
	/**
	 * dialog buttons : yes, no, cancel
	 */
	Object[] dialogButtons;
	/**
	 * dialog button: ok
	 */
	private Object[] okButton = new Object[]{"Ok"};
	
	/**
	 * Application constructor.
	 */
	public JNotepadPP() {
		setTitle("JNotepad++");
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		
		initGUI();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitFunction();
			}
			
		});
		
		mdm.setFocusCycleRoot(true);
		
		mdm.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println("Tab: " + mdm.getSelectedIndex());
				
				if (mdm.getSelectedIndex() >= 0) {
					SingleDocumentModel doc = mdm.getDocument(mdm.getSelectedIndex());
					mdm.setCurrentDocument(doc);
					
					if (doc.isModified()) {
						saveDocumentAction.setEnabled(true);
						saveAsDocumentAction.setEnabled(true);
					} else {
						saveDocumentAction.setEnabled(false);
						saveAsDocumentAction.setEnabled(false);
					}
					
					if (doc.getTextComponent().getText().length() > 0) {
						cutSelectedPartAction.setEnabled(true);
						copySelectedPartAction.setEnabled(true);
					} else {
						cutSelectedPartAction.setEnabled(false);
						copySelectedPartAction.setEnabled(false);
					}

					caretInfo();
					
					if (mdm.getCurrentDocument().getTextComponent().getSelectedText() != null) {
						uppercaseAction.setEnabled(true);
						lowercaseAction.setEnabled(true);
						invertcaseAction.setEnabled(true);
						
						ascendingAction.setEnabled(true);
						descendingAction.setEnabled(true);
						
						uniqueAction.setEnabled(true);
					} else {
						uppercaseAction.setEnabled(false);
						lowercaseAction.setEnabled(false);
						invertcaseAction.setEnabled(false);
						
						ascendingAction.setEnabled(false);
						descendingAction.setEnabled(false);
						
						uniqueAction.setEnabled(false);
					}
					
				} else {
					mdm.setCurrentDocument(null);
					
					saveDocumentAction.setEnabled(false);
					saveAsDocumentAction.setEnabled(false);
					closeDocumentAction.setEnabled(false);
					
					cutSelectedPartAction.setEnabled(false); 
					copySelectedPartAction.setEnabled(false);
					pasteOptionAction.setEnabled(false);
					
					uppercaseAction.setEnabled(false);
					lowercaseAction.setEnabled(false);
					invertcaseAction.setEnabled(false);
					
					ascendingAction.setEnabled(false);
					descendingAction.setEnabled(false);
					
					uniqueAction.setEnabled(false);
					
					len.setText(" " + flp.getString("length") +" : 0");
					ln.setText(" " + flp.getString("ln") + " : 0 " + flp.getString("col") + " : 0 " + flp.getString("selected") + " : 0");
				}
				
			}
		});
		
		mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel == null) {
					setTitle("JNotepad++");
				} else if (currentModel.getFilePath() == null) {
					setTitle("(unnamed) - JNotepad++");
				} else {
					setTitle(currentModel.getFilePath().getFileName().toString() + " - JNotepad++");
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				closeDocumentAction.setEnabled(true);
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				
				mdm.removeTabAt(mdm.getSelectedIndex());
				
				if (mdm.getNumberOfDocuments() < 1) {
					closeDocumentAction.setEnabled(false);
				} else {
					closeDocumentAction.setEnabled(true);
				}
			}
			
		});
	}
	
	/**
	 * Function for GUI components initialization.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		
		createActions();
		
		createMenus();
		createToolbars();
		
		this.getContentPane().add(mdm.getVisualComponent(), BorderLayout.CENTER);
	}
	
	/**
	 * Function for loading image icon.
	 * @param path image icon file path
	 * @return image icon
	 * @throws IOException if problem with reading file occurs
	 */
	private ImageIcon getImageIcon(String path) throws IOException {
		byte[] bytes;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) throw new IOException();
			
			bytes = is.readAllBytes();
		}

		return new ImageIcon(bytes);
	}
	
	/**
	 * Function that creates tab name.
	 * @return tab name
	 */
	private String tabToolpitText() {
		if (mdm.getCurrentDocument().getFilePath() == null) {
			return "unnamed";
		} else {
			return mdm.getCurrentDocument().getFilePath().toString();
		}
	}
	
	/**
	 * Function for exiting application. If some files are modified and not saved,
	 * user is asked for each one of them before application disposes.
	 */
	private void exitFunction() {
		dialogButtons = new Object[]{ flp.getString("yes"),
	            flp.getString("no"), flp.getString("cancel") };
		
		if (mdm.getNumberOfDocuments() > 0) {
			for (int i = 0; i < mdm.getNumberOfDocuments(); i++) {
				SingleDocumentModel doc = mdm.getDocument(i);
				mdm.setCurrentDocument(doc);
				
				if (doc.isModified()) {
					Path path = doc.getFilePath();
					String filename;
					if (path == null) {
						filename = "unnamed";
					} else {
						filename = path.getFileName().toString();
					}
					
					int option = JOptionPane.showOptionDialog(null, LocalizationProvider.getInstance().getString("saveChanges") + " " + filename + "?",
							"JNotepad++", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, dialogButtons, dialogButtons[2]);
					
					if (option == JOptionPane.CANCEL_OPTION) {
						return;
					} else if (option == JOptionPane.YES_OPTION) {
						saveDocumentAction.actionPerformed(null);
					} 
				} 
			}
		}

		dispose();
	}
	
	/**
	 * Action closes the document, removes it from the mutiple document model.
	 */
	private Action closeDocumentAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mdm.closeDocument(mdm.getCurrentDocument());
		}
	};
	
	/**
	 * Action that calculates and returns current document statistical information.
	 * If there are no opened document, user gets notification message.
	 */
	private Action statisticalInfo = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//okButton[0] = flp.getString("ok");
			
			if (mdm.getCurrentDocument() == null) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje1"),
						LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE,
						null, okButton, null);
				
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Nema otvorenih dokumenata!", 
						"Warning",
						JOptionPane.WARNING_MESSAGE);*/
				return;
			} else  {
				int x = mdm.getCurrentDocument().getTextComponent().getDocument().getLength();
				
				String tekst;
				try {
					tekst = mdm.getCurrentDocument().getTextComponent().getDocument().getText(0, x);
					
					int y = 0;
					for (int i = 0; i < x; i++) {
						if (tekst.charAt(i) != ' ' && tekst.charAt(i) != '\n'&&
								tekst.charAt(i) != '\t' && tekst.charAt(i) != '\r') {
							y++;
						}
						
					}
					
					int z = mdm.getCurrentDocument().getTextComponent().getLineOfOffset(x) + 1;
					
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							flp.getString("stats1") + " " + x + " " +
							flp.getString("stats2") + " " + y + " " + 
							flp.getString("stats3") + " " + z + " " + flp.getString("stats4"), 
							flp.getString("stats"), 
							JOptionPane.INFORMATION_MESSAGE);
					
					/*JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Your document has " + x +
							" characters, " + y + " non-blank characters and " + z + " lines.", 
							"Statistical Info", 
							JOptionPane.INFORMATION_MESSAGE);*/
					return;
					
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	};
	
	/**
	 * Function calculates and updates caret information to status bar.
	 */
	private void caretInfo() {
		int line = 0;
		int column = 0;
		int length = 0;
		int selected = 0;
		
		if (mdm.getNumberOfDocuments() > 0) {
			int offset = mdm.getCurrentDocument().getTextComponent().getCaretPosition();
			
			try {
				line = mdm.getCurrentDocument().getTextComponent().getLineOfOffset(offset) + 1;
				
				try {
					column = offset - mdm.getCurrentDocument().getTextComponent().getLineStartOffset(line - 1) + 1;
					
					selected = 0;
					if (mdm.getCurrentDocument().getTextComponent().getSelectedText() != null) {
						selected = mdm.getCurrentDocument().getTextComponent().getSelectedText().length();
					}
					
					length = mdm.getCurrentDocument().getTextComponent().getDocument().getLength();
					
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
		len.setText(" " + flp.getString("length") + " : " + length);
		ln.setText(" " + flp.getString("ln") + " : " + line + " " + flp.getString("col") + " : " + column + " "  + flp.getString("selected") + " : " + selected);
	}
	
	/**
	 * Action opens the selected document from JFileChooser and add it to the tabbed pane.
	 * If there are any problems with loading the file, user gets appropriate notification.
	 */
	private Action openDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			for (SingleDocumentModel doc : mdm) {
				if (doc.getFilePath() != null) {
					if (doc.getFilePath().equals(filePath)) {
						JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("file") + " " + fileName.getAbsolutePath() + " " + flp.getString("upozorenje2"),
								LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE,
								null, okButton, null);
						
						/*JOptionPane.showMessageDialog(
								JNotepadPP.this, 
								"Datoteka "+fileName.getAbsolutePath()+" je već otvorena!", 
								"Pogreška", 
								JOptionPane.ERROR_MESSAGE);*/
						return;
					}
				}
			}
			
			if(!Files.isReadable(filePath)) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("file") + " " + fileName.getAbsolutePath() + " " + flp.getString("upozorenje3"),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE,
						null, okButton, null);
				
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);*/
				return;
			}
			
			SingleDocumentModel doc = mdm.loadDocument(filePath);
			
			if (doc == null) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje4") + " " + fileName.getAbsolutePath() + ".",
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE,
						null, okButton, null);
				
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom čitanja datoteke "+fileName.getAbsolutePath()+".", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);*/
				return;
			} else {
				doc.addSingleDocumentListener(new SingleDocumentListener() {

					@Override
					public void documentModifyStatusUpdated(SingleDocumentModel model) {
						if (model.isModified()) {
							saveDocumentAction.setEnabled(true);
							saveAsDocumentAction.setEnabled(true);
							
							ImageIcon icon;
							try {
								icon = getImageIcon("icons/modified.png");
								mdm.setIconAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), icon);
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						} else {
							saveDocumentAction.setEnabled(false);
							saveAsDocumentAction.setEnabled(false);
							
							ImageIcon icon;
							try {
								icon = getImageIcon("icons/unmodified.png");
								mdm.setIconAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), icon);
								
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

					@Override
					public void documentFilePathUpdated(SingleDocumentModel model) {
						mdm.setTitleAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), model.getFilePath().getFileName().toString());
						mdm.getCurrentDocument().setModified(false);
					}
					
				});
				
				doc.getTextComponent().getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						doc.setModified(true);

						if (doc.getTextComponent().getText().length() > 0) {
							cutSelectedPartAction.setEnabled(true);
							copySelectedPartAction.setEnabled(true);
						} else {
							cutSelectedPartAction.setEnabled(false);
							copySelectedPartAction.setEnabled(false);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						doc.setModified(true);

						if (doc.getTextComponent().getText().length() > 0) {
							cutSelectedPartAction.setEnabled(true);
							copySelectedPartAction.setEnabled(true);
						} else {
							cutSelectedPartAction.setEnabled(false);
							copySelectedPartAction.setEnabled(false);
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						doc.setModified(true);

						if (doc.getTextComponent().getText().length() > 0) {
							cutSelectedPartAction.setEnabled(true);
							copySelectedPartAction.setEnabled(true);
						} else {
							cutSelectedPartAction.setEnabled(false);
							copySelectedPartAction.setEnabled(false);
						}
					}
					
				});
				
				doc.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						caretInfo();
						
						if (mdm.getCurrentDocument().getTextComponent().getSelectedText() != null) {
							uppercaseAction.setEnabled(true);
							lowercaseAction.setEnabled(true);
							invertcaseAction.setEnabled(true);
							
							ascendingAction.setEnabled(true);
							descendingAction.setEnabled(true);
							
							uniqueAction.setEnabled(true);
						} else {
							uppercaseAction.setEnabled(false);
							lowercaseAction.setEnabled(false);
							invertcaseAction.setEnabled(false);
							
							ascendingAction.setEnabled(false);
							descendingAction.setEnabled(false);
							
							uniqueAction.setEnabled(false);
						}
					}
					
				});
				
				try {
					mdm.addTab(mdm.getCurrentDocument().getFilePath().getFileName().toString(), getImageIcon("icons/unmodified.png"), 
							new JScrollPane(mdm.getCurrentDocument().getTextComponent()), tabToolpitText());
					mdm.setSelectedIndex(mdm.getNumberOfDocuments() - 1);
					
					len.setText(" " + flp.getString("length") + " : " + mdm.getCurrentDocument().getTextComponent().getDocument().getLength());
					ln.setText(" " + flp.getString("ln") + " : 1 " + flp.getString("col") + " : 1 " + flp.getString("selected") + " : 0");
					
					pasteOptionAction.setEnabled(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
	};
	
	/**
	 * Action adds new, empty document to the tabbed pane and 
	 * adds it to the multiple document model with path null.
	 */
	private Action newDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = mdm.createNewDocument();
			
			doc.addSingleDocumentListener(new SingleDocumentListener() {

				@Override
				public void documentModifyStatusUpdated(SingleDocumentModel model) {
					if (model.isModified()) {
						saveDocumentAction.setEnabled(true);
						saveAsDocumentAction.setEnabled(true);
						
						ImageIcon icon;
						try {
							icon = getImageIcon("icons/modified.png");
							mdm.setIconAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), icon);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					} else {
						saveDocumentAction.setEnabled(false);
						saveAsDocumentAction.setEnabled(false);
						
						ImageIcon icon;
						try {
							icon = getImageIcon("icons/unmodified.png");
							mdm.setIconAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), icon);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				@Override
				public void documentFilePathUpdated(SingleDocumentModel model) {
					mdm.setTitleAt(mdm.getIndexOfDocument(mdm.getCurrentDocument()), model.getFilePath().getFileName().toString());
					mdm.getCurrentDocument().setModified(false);
				}
				
			});
			
			doc.getTextComponent().getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					doc.setModified(true);

					if (doc.getTextComponent().getText().length() > 0) {
						cutSelectedPartAction.setEnabled(true);
						copySelectedPartAction.setEnabled(true);
					} else {
						cutSelectedPartAction.setEnabled(false);
						copySelectedPartAction.setEnabled(false);
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					doc.setModified(true);

					if (doc.getTextComponent().getText().length() > 0) {
						cutSelectedPartAction.setEnabled(true);
						copySelectedPartAction.setEnabled(true);
					} else {
						cutSelectedPartAction.setEnabled(false);
						copySelectedPartAction.setEnabled(false);
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					doc.setModified(true);

					if (doc.getTextComponent().getText().length() > 0) {
						cutSelectedPartAction.setEnabled(true);
						copySelectedPartAction.setEnabled(true);
					} else {
						cutSelectedPartAction.setEnabled(false);
						copySelectedPartAction.setEnabled(false);
					}
				}
				
			});
			
			doc.getTextComponent().addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					caretInfo();
					
					if (mdm.getCurrentDocument().getTextComponent().getSelectedText() != null) {
						uppercaseAction.setEnabled(true);
						lowercaseAction.setEnabled(true);
						invertcaseAction.setEnabled(true);
						
						ascendingAction.setEnabled(true);
						descendingAction.setEnabled(true);
						
						uniqueAction.setEnabled(true);
					} else {
						uppercaseAction.setEnabled(false);
						lowercaseAction.setEnabled(false);
						invertcaseAction.setEnabled(false);
						
						ascendingAction.setEnabled(false);
						descendingAction.setEnabled(false);
						
						uniqueAction.setEnabled(false);
					}
				}
				
			});

			try {
				mdm.addTab("unnamed", getImageIcon("icons/unmodified.png"), 
						new JScrollPane(mdm.getCurrentDocument().getTextComponent()), tabToolpitText());
				mdm.setSelectedIndex(mdm.getNumberOfDocuments() - 1);
				
				len.setText(" " + flp.getString("length") + " : " + mdm.getCurrentDocument().getTextComponent().getDocument().getLength());
				ln.setText(" " + flp.getString("ln") + " : 1 " + flp.getString("col") + " : 1 " + flp.getString("selected") + " : 0");
				
				pasteOptionAction.setEnabled(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		}
	};
	
	/**
	 * Action saves the document to the document path or if document does not have specified path, 
	 * to the path chosen with JFileChooser.
	 * If there are any problems with saving the file, user gets appropriate notification.
	 */
	private Action saveDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Path filePath = mdm.getCurrentDocument().getFilePath();
			
			if(filePath==null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				
				if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje5"),
							LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE,
							null, okButton, null);
					
					/*JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Ništa nije snimljeno.", 
							"Upozorenje", 
							JOptionPane.WARNING_MESSAGE);*/
					return;
				}
				filePath = jfc.getSelectedFile().toPath();
				
				for (SingleDocumentModel doc : mdm) {
					if (doc.getFilePath() != null) {
						if (doc.getFilePath().equals(filePath)) {
							JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("file") + " " + filePath.toString() + " " + flp.getString("upozorenje6"),
									LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE,
									null, okButton, null);
							
							/*JOptionPane.showMessageDialog(
									JNotepadPP.this, 
									"Datoteka "+ filePath.toString() +" je otvorena u aplikaciji!\nMolim Vas zatvorite ju kako bi nastavili!", 
									"Upozorenje", 
									JOptionPane.WARNING_MESSAGE);*/
							return;
						}
					}
				}
			}
			
			byte[] podatci = mdm.getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(filePath, podatci);
				
			} catch (IOException e1) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje71") + " " + filePath.toFile().getAbsolutePath() + " " + flp.getString("upozorenje72"),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE,
						null, okButton, null);
				
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom zapisivanja datoteke "+filePath.toFile().getAbsolutePath()+".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);*/
				return;
			}
			
			mdm.saveDocument(mdm.getCurrentDocument(), filePath);
			mdm.getCurrentDocument().setFilePath(filePath);
			mdm.getCurrentDocument().setModified(false);
			
			JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("obavijest1"),
					LocalizationProvider.getInstance().getString("information"), JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
					null, okButton, null);
			
			/*JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);*/
		}
	};
	
	/**
	 * Action saves file to the path chosen with JFileChooser.
	 * If there are any problems with saving the file, user gets appropriate notification.
	 */
	private Action saveAsDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document as");
				
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje5"),
						LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE,
						null, okButton, null);
				
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);*/
				return;
			}
			Path filePath = jfc.getSelectedFile().toPath();
			File file = filePath.toFile();
			
			for (SingleDocumentModel doc : mdm) {
				if (doc.getFilePath() != null) {
					if (doc.getFilePath().equals(filePath)) {
						JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("file") + " " + filePath.toString() + " " + flp.getString("upozorenje6"),
								LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE,
								null, okButton, null);
						
						/*JOptionPane.showMessageDialog(
								JNotepadPP.this, 
								"Datoteka "+ filePath.toString() +" je otvorena u aplikaciji!\nMolim Vas zatvorite ju kako bi nastavili!", 
								"Upozorenje", 
								JOptionPane.WARNING_MESSAGE);*/
						return;
					}
				}
			}
			
			if(file.exists()) {
				int option = JOptionPane.showOptionDialog(null, "Dokument već postoji na putanji " + filePath + ". Želite li nastaviti?",
						"Save as", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, null, null);
				
				if (option == JOptionPane.NO_OPTION) {
					return;
				}
			}
				
			byte[] podatci = mdm.getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(filePath, podatci);
						
			} catch (IOException e1) {
				JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("upozorenje71") + " " + filePath.toFile().getAbsolutePath() + " " + flp.getString("upozorenje72"),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE,
						null, okButton, null);
						
						
					/*JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Pogreška prilikom zapisivanja datoteke "+filePath.toFile().getAbsolutePath()+".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
							"Pogreška", 
							JOptionPane.ERROR_MESSAGE);*/
				return;
			}
					
			mdm.saveDocument(mdm.getCurrentDocument(), filePath);
			mdm.getCurrentDocument().setFilePath(filePath);
			mdm.getCurrentDocument().setModified(false);
					
			JOptionPane.showOptionDialog(JNotepadPP.this, flp.getString("obavijest1"),
					LocalizationProvider.getInstance().getString("information"), JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE,
					null, okButton, null);
					
				/*JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka je snimljena.", 
						"Informacija", 
						JOptionPane.INFORMATION_MESSAGE);*/
		}
			
	};
	
	/**
	 * Action starts function for exiting application.
	 */
	private Action exitApplicationAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			exitFunction();
		}
	};
	
	/**
	 * Action cuts selected part of the text.
	 * Enabled only when document contains at least one character.
	 */
	private Action cutSelectedPartAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			mdm.getCurrentDocument().getTextComponent().cut();

		}
	};
	
	/**
	 * Action copies selected part of the text.
	 * Enabled only when document contains at least one character.
	 */
	private Action copySelectedPartAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			mdm.getCurrentDocument().getTextComponent().copy();
			
		}
	};
	
	/**
	 * Action pastes copied text.
	 */
	private Action pasteOptionAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			mdm.getCurrentDocument().getTextComponent().paste();

		}
	};
	
	/**
	 * Action performs to uppercase on the selected part of the text.
	 * Enabled only when there is selected text.
	 */
	private Action uppercaseAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			mdm.getCurrentDocument().getTextComponent().replaceSelection(mdm.getCurrentDocument().getTextComponent().getSelectedText().toUpperCase());
		}
	};
	
	/**
	 * Action performs to lowercase on the selected part of the text.
	 * Enabled only when there is selected text.
	 */
	private Action lowercaseAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			mdm.getCurrentDocument().getTextComponent().replaceSelection(mdm.getCurrentDocument().getTextComponent().getSelectedText().toLowerCase());
		}
	};
	
	/**
	 * Action inverts letter case on the selected part of the text.
	 * Enabled only when there is selected text.
	 */
	private Action invertcaseAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			char[] text = mdm.getCurrentDocument().getTextComponent().getSelectedText().toCharArray();
			StringBuilder textSb = new StringBuilder();
			
			for (Character c : text) {
				if (Character.isLowerCase(c)) {
					textSb.append(Character.toUpperCase(c));
				} else {
					textSb.append(Character.toLowerCase(c));
				}
			}
			
			mdm.getCurrentDocument().getTextComponent().replaceSelection(textSb.toString());
		}
	};
	
	/**
	 * Action sorts lines in ascending order on the selected part of the text.
	 * If user selection spans only part of some line, whole line is affected.
	 * Enabled only when there is selected text.
	 */
	private Action ascendingAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Collator col = Collator.getInstance(Locale.of(LocalizationProvider.getInstance().getLanguage()));
			
			StringBuilder sortSb = new StringBuilder();
			
			JTextComponent component = mdm.getCurrentDocument().getTextComponent();
			int positionEnd = component.getSelectionEnd();
			int positionStart = component.getSelectionStart();
			
			Document doc = component.getDocument();
			Element root = doc.getDefaultRootElement();
			
			int rowStart = root.getElementIndex(positionStart);
			int columnStart = positionStart - root.getElement(rowStart).getStartOffset();
			
			int rowEnd = root.getElementIndex(positionEnd);
			
			Position p = doc.getEndPosition();
			
			int endElement = root.getElement(rowEnd).getEndOffset() == p.getOffset() ? root.getElement(rowEnd).getEndOffset() - 1 : root.getElement(rowEnd).getEndOffset();
			int columnEnd = endElement - positionEnd;
			
			try {
				positionStart = positionStart - columnStart;
				positionEnd = positionEnd + columnEnd;
				
				String text = doc.getText(positionStart, positionEnd - positionStart);
				String[] lines = text.split("\n");
				
				ArrayList<String> linesList = new ArrayList<>();
				for (String line : lines) {
					linesList.add(line);
				}
				Collections.sort(linesList, col);
				
				for (String line : linesList) {
					sortSb.append(line);
					sortSb.append("\n");
				}
				
				doc.remove(positionStart, positionEnd - positionStart);
				doc.insertString(positionStart, sortSb.toString(), null);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Action sorts lines in descending order on the selected part of the text.
	 * If user selection spans only part of some line, whole line is affected.
	 * Enabled only when there is selected text.
	 */
	private Action descendingAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Collator col = Collator.getInstance(Locale.of(LocalizationProvider.getInstance().getLanguage()));
			
			StringBuilder sortSb = new StringBuilder();
			
			JTextComponent component = mdm.getCurrentDocument().getTextComponent();
			int positionEnd = component.getSelectionEnd();
			int positionStart = component.getSelectionStart();
			
			Document doc = component.getDocument();
			Element root = doc.getDefaultRootElement();
			
			int rowStart = root.getElementIndex(positionStart);
			int columnStart = positionStart - root.getElement(rowStart).getStartOffset();
			
			int rowEnd = root.getElementIndex(positionEnd);
			
			Position p = doc.getEndPosition();
			
			int endElement = root.getElement(rowEnd).getEndOffset() == p.getOffset() ? root.getElement(rowEnd).getEndOffset() - 1 : root.getElement(rowEnd).getEndOffset();
			int columnEnd = endElement - positionEnd;
			
			try {
				positionStart = positionStart - columnStart;
				positionEnd = positionEnd + columnEnd;
				
				String text = doc.getText(positionStart, positionEnd - positionStart);
				String[] lines = text.split("\n");
				
				ArrayList<String> linesList = new ArrayList<>();
				for (String line : lines) {
					linesList.add(line);
				}
				Collections.sort(linesList, col.reversed());
				
				for (String line : linesList) {
					sortSb.append(line);
					sortSb.append("\n");
				}
				
				doc.remove(positionStart, positionEnd - positionStart);
				doc.insertString(positionStart, sortSb.toString(), null);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Action removesall lines which are duplicates (only the first occurrence is retained) 
	 * on the selected part of the text.
	 * If user selection spans only part of some line, whole line is affected.
	 * Enabled only when there is selected text.
	 */
	private Action uniqueAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuilder sortSb = new StringBuilder();
			
			JTextComponent component = mdm.getCurrentDocument().getTextComponent();
			int positionEnd = component.getSelectionEnd();
			int positionStart = component.getSelectionStart();
			
			Document doc = component.getDocument();
			Element root = doc.getDefaultRootElement();
			
			int rowStart = root.getElementIndex(positionStart);
			int columnStart = positionStart - root.getElement(rowStart).getStartOffset();
			
			int rowEnd = root.getElementIndex(positionEnd);
			
			Position p = doc.getEndPosition();
			
			int endElement = root.getElement(rowEnd).getEndOffset() == p.getOffset() ? root.getElement(rowEnd).getEndOffset() - 1 : root.getElement(rowEnd).getEndOffset();
			int columnEnd = endElement - positionEnd;
			
			try {
				positionStart = positionStart - columnStart;
				positionEnd = positionEnd + columnEnd;
				
				String text = doc.getText(positionStart, positionEnd - positionStart);
				String[] lines = text.split("\n");
				
				Set<String> linesList = new LinkedHashSet<>();
				for (String line : lines) {
					linesList.add(line);
				}
				
				for (String line : linesList) {
					sortSb.append(line);
					sortSb.append("\n");
				}
				
				doc.remove(positionStart, positionEnd - positionStart);
				doc.insertString(positionStart, sortSb.toString(), null);
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Action sets LocalizationProvider language to Croatian.
	 */
	private Action setHrLanguage = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");

		}

	};

	/**
	 * Action sets LocalizationProvider language to English.
	 */
	private Action setEnLanguage = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}

	};
	
	/**
	 * Action sets LocalizationProvider language to German.
	 */
	private Action setDeLanguage = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}

	};
	
	/**
	 * Function creates actions.
	 */
	private void createActions() {
		openDocumentAction.putValue(
				Action.NAME, 
				"Open");
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to open existing file from disk."); 
		
		newDocumentAction.putValue(
				Action.NAME, 
				LocalizationProvider.getInstance().getString("new"));
		newDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")); 
		newDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N); 
		newDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used for creating new file."); 
		
		saveDocumentAction.putValue(
				Action.NAME, 
				"Save");
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk.");
		
		saveAsDocumentAction.putValue(
				Action.NAME, 
				"Save As");
		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift S")); 
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.CTRL_DOWN_MASK); //?
		saveAsDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file on specific location on disk.");
		
		closeDocumentAction.putValue(
				Action.NAME, 
				"Close");
		closeDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Close current document.");
		
		exitApplicationAction.putValue(
				Action.NAME, 
				"Exit");
		exitApplicationAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Exit application.");
		
		cutSelectedPartAction.putValue(
				Action.NAME, 
				"Cut");
		cutSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X")); 
		cutSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		cutSelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Cut selected part of the text."); 
		
		copySelectedPartAction.putValue(
				Action.NAME, 
				"Copy");
		copySelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")); 
		copySelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C); 
		copySelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Copy selected part of the text.");
		
		pasteOptionAction.putValue(
				Action.NAME, 
				"Paste");
		pasteOptionAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control V")); 
		pasteOptionAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_V); 
		pasteOptionAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Paste text.");
		
		uppercaseAction.putValue(
				Action.NAME, 
				"to uppercase");
		uppercaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Converts characters to uppercase.");
		
		lowercaseAction.putValue(
				Action.NAME, 
				"to lowercase");
		lowercaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Converts characters to lowercase.");
		
		invertcaseAction.putValue(
				Action.NAME, 
				"invert case");
		invertcaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Inverts characters case.");
		
		ascendingAction.putValue(
				Action.NAME, 
				"Ascending");
		ascendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Ascending sort of currently selected items.");
		
		descendingAction.putValue(
				Action.NAME, 
				"Descending");
		descendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Descending sort of currently selected items.");
		
		uniqueAction.putValue(
				Action.NAME, 
				"Unique");
		uniqueAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Removes from selection all lines which are duplicates (only the first occurrence is retained).");
		
		saveDocumentAction.setEnabled(false);
		saveAsDocumentAction.setEnabled(false);
		closeDocumentAction.setEnabled(false);
		
		cutSelectedPartAction.setEnabled(false); 
		copySelectedPartAction.setEnabled(false);
		pasteOptionAction.setEnabled(false);
		
		uppercaseAction.setEnabled(false);
		lowercaseAction.setEnabled(false);
		invertcaseAction.setEnabled(false);
		
		ascendingAction.setEnabled(false);
		descendingAction.setEnabled(false);
		
		uniqueAction.setEnabled(false);
		
		statisticalInfo.putValue(
				Action.NAME, 
				"Statistical Info");
		
		setHrLanguage.putValue(Action.NAME, "Hrvatski");
		setEnLanguage.putValue(Action.NAME, "English");
		setDeLanguage.putValue(Action.NAME, "Deutsch");
	}

	/**
	 * Function creates application menu bars.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		//JMenu fileMenu = new JMenu("File");
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;
		});
		fileMenu.setText(LocalizationProvider.getInstance().getString("file"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fileMenu.setText(LocalizationProvider.getInstance().getString("file"));
			}
		});

		menuBar.add(fileMenu);
		
		JMenuItem newItem = new JMenuItem(newDocumentAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				newItem.setText(LocalizationProvider.getInstance().getString("new"));
			}
		});
		fileMenu.add(newItem);
		
		JMenuItem openItem = new JMenuItem(openDocumentAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				openItem.setText(LocalizationProvider.getInstance().getString("open"));
			}
		});
		fileMenu.add(openItem);
		
		JMenuItem saveItem = new JMenuItem(saveDocumentAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				saveItem.setText(LocalizationProvider.getInstance().getString("save"));
			}
		});
		fileMenu.add(saveItem);
		
		JMenuItem saveAsItem = new JMenuItem(saveAsDocumentAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				saveAsItem.setText(LocalizationProvider.getInstance().getString("saveAs"));
			}
		});
		fileMenu.add(saveAsItem);
		
		fileMenu.addSeparator();
		
		JMenuItem closeItem = new JMenuItem(closeDocumentAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				closeItem.setText(LocalizationProvider.getInstance().getString("close"));
			}
		});
		fileMenu.add(closeItem);
		
		fileMenu.addSeparator();
		
		JMenuItem exitItem = new JMenuItem(exitApplicationAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				exitItem.setText(LocalizationProvider.getInstance().getString("exit"));
			}
		});
		fileMenu.add(exitItem);
		
		//JMenu editMenu = new JMenu("Edit");
		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;
			
		});
		editMenu.setText(LocalizationProvider.getInstance().getString("edit"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				editMenu.setText(LocalizationProvider.getInstance().getString("edit"));
			}
		});

		menuBar.add(editMenu);
		
		JMenuItem cutItem = new JMenuItem(cutSelectedPartAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				cutItem.setText(LocalizationProvider.getInstance().getString("cut"));
			}
		});
		editMenu.add(cutItem);
		
		JMenuItem copyItem = new JMenuItem(copySelectedPartAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				copyItem.setText(LocalizationProvider.getInstance().getString("copy"));
			}
		});
		editMenu.add(copyItem);
		
		JMenuItem pasteItem = new JMenuItem(pasteOptionAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				pasteItem.setText(LocalizationProvider.getInstance().getString("paste"));
			}
		});
		editMenu.add(pasteItem);
		
		JMenu toolMenu = new JMenu(new LocalizableAction("alati", flp) {
			private static final long serialVersionUID = 1L;
		});
		toolMenu.setText(LocalizationProvider.getInstance().getString("alati"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				toolMenu.setText(LocalizationProvider.getInstance().getString("alati"));
			}
		});
		
		JMenu caseMenu = new JMenu(new LocalizableAction("case", flp) {
			private static final long serialVersionUID = 1L;
		});
		caseMenu.setText(LocalizationProvider.getInstance().getString("case"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				caseMenu.setText(LocalizationProvider.getInstance().getString("case"));
			}
		});

		toolMenu.add(caseMenu);
		
		JMenu sortMenu = new JMenu(new LocalizableAction("sort", flp) {
			private static final long serialVersionUID = 1L;
		});
		sortMenu.setText(LocalizationProvider.getInstance().getString("sort"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				sortMenu.setText(LocalizationProvider.getInstance().getString("sort"));
			}
		});

		toolMenu.add(sortMenu);
		
		JMenuItem uniqueItem = new JMenuItem(uniqueAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				uniqueItem.setText(LocalizationProvider.getInstance().getString("unique"));
			}
		});
		
		toolMenu.add(uniqueItem);

		menuBar.add(toolMenu);
		
		JMenuItem uppercaseItem = new JMenuItem(uppercaseAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				uppercaseItem.setText(LocalizationProvider.getInstance().getString("uppercase"));
			}
		});
		caseMenu.add(uppercaseItem);
		
		JMenuItem lowercaseItem = new JMenuItem(lowercaseAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				lowercaseItem.setText(LocalizationProvider.getInstance().getString("lowercase"));
			}
		});
		caseMenu.add(lowercaseItem);
		
		JMenuItem invertcaseItem = new JMenuItem(invertcaseAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				invertcaseItem.setText(LocalizationProvider.getInstance().getString("invertcase"));
			}
		});
		caseMenu.add(invertcaseItem);
		
		JMenuItem ascendingItem = new JMenuItem(ascendingAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				ascendingItem.setText(LocalizationProvider.getInstance().getString("ascending"));
			}
		});
		sortMenu.add(ascendingItem);
		
		JMenuItem descendingItem = new JMenuItem(descendingAction);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				descendingItem.setText(LocalizationProvider.getInstance().getString("descending"));
			}
		});
		sortMenu.add(descendingItem);
		
		JMenu langMenu = new JMenu(new LocalizableAction("lang", flp) {
			private static final long serialVersionUID = 1L;
		});
		langMenu.setText(LocalizationProvider.getInstance().getString("lang"));
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				langMenu.setText(LocalizationProvider.getInstance().getString("lang"));
			}
		});

		menuBar.add(langMenu);
		
		langMenu.add(new JMenuItem(setHrLanguage));
		langMenu.add(new JMenuItem(setEnLanguage));
		langMenu.add(new JMenuItem(setDeLanguage));
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Function creates application toolbars.
	 */
	private void createToolbars() {
		toolBar = new JToolBar(flp.getString("alati"));
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				JNotepadPP.this.toolBar.setName(LocalizationProvider.getInstance().getString("alati"));
			}
		});
		
		toolBar.setFloatable(true);
		toolBar.setVisible(true);
		
		statsButton = new JButton(statisticalInfo);
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				JNotepadPP.this.statsButton.setText(LocalizationProvider.getInstance().getString("stats"));
			}
		});
		
		toolBar.add(statsButton);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		JToolBar statusBar = new JToolBar();
		
		statusBar.setLayout(new GridLayout(1,0));
		statusBar.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.GRAY));
		statusBar.setFloatable(true);
		statusBar.setVisible(true);
		
		len = new JLabel(" " + flp.getString("length") + " : 0");
		statusBar.add(len);
		ln = new JLabel(" " + flp.getString("ln") + " : 0 " + flp.getString("col") + " : 0 " + flp.getString("selected") + " : 0");
		ln.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		statusBar.add(ln);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				caretInfo();
			}
		});
		
		final Sat time = new Sat();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				time.zaustavi();
			}
		});

		time.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		statusBar.add(time);
		
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}
	
	/**
	 * Class that represents date and time.
	 * @author Katraina Gacina
	 *
	 */
	static class Sat extends JComponent {

		private static final long serialVersionUID = 1L;
		
		/**
		 * time
		 */
		volatile String vrijeme;
		
		/**
		 * request status
		 */
		volatile boolean stopRequested = false;
		
		/**
		 * date and time formatter
		 */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		
		/**
		 * Constructor. Creates new Thread to measure time.
		 */
		public Sat() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					if(stopRequested) break;
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		/**
		 * Function which is called when clock should be stopped.
		 */
		private void zaustavi() {
			stopRequested = true;
		}
		
		/**
		 * Function which updates time representation to user.
		 */
		private void updateTime() {
			vrijeme = formatter.format(LocalDateTime.now());
			repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Insets ins = getInsets();
			Dimension dim = getSize();
			Rectangle r = new Rectangle(
					ins.left, 
					ins.top, 
					dim.width-ins.left-ins.right,
					dim.height-ins.top-ins.bottom);
			if(isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			g.setColor(getForeground());
			
			FontMetrics fm = g.getFontMetrics();
			int w = fm.stringWidth(vrijeme);
			int h = fm.getAscent();
			
			g.drawString(vrijeme, (r.width-w), r.y+r.height-(r.height-h)/2);
		}
	}
	
	/**
	 * Main program which starts JNotepadPP application.
	 * @param args no arguments are used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
