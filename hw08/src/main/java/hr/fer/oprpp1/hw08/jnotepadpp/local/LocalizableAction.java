package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class that is a representation of an Action.
 * @author Katarina Gacina
 *
 */
public class LocalizableAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * key
	 */
	private String key;
	
	/**
	 * ILocalizationProvider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Constructor. It asks lp for translation of the key and then calls on Action object putValue(NAME, translation)
	 * @param key 
	 * @param lp ILocalizationProvider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		
		this.putValue(key, lp.getString(key));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lp.addLocalizationListener(new ILocalizationListener() {
			
			/**
			 * Function asks lp to give you a new translation of action's key and you must again call putValue(NAME, translation).
			 *  Since this method changes action's properties, the action itself will notify all interested listeners about the change.
			 */
			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(key, lp.getString(key));
			}
			
		});
	}

}
