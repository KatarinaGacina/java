package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that represents localization provider.
 * @author Katarina Gacina
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Function adds specified ILocalizationListener.
	 * @param listener specified ILocalizationListener
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Function removes specified ILocalizationListener
	 * @param listener specified ILocalizationListener
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Function returns localized representation of the given key.
	 * @param key
	 * @return
	 */
	String getString(String key);
	
	/**
	 * Function returns current language.
	 * @return current language
	 */
	String getLanguage();
	
}
