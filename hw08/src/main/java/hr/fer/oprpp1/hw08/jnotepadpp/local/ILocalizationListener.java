package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that represents ILocalizationProvider listener.
 * @author Katarina Gacina
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Function which will be called by the subject when language changes.
	 */
	void localizationChanged();
	
}
