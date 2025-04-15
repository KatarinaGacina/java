package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that implements ILocalizationProvider and is able to give the translations for given keys.
 * Singleton object.
 * @author Katarina Gacina
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private static LocalizationProvider instance = null;
	
	private String language;
	private ResourceBundle bundle;
	
	/**
	 * Private constructor.
	 * Sets language to "en" by default.
	 */
	private LocalizationProvider() {
		this.language = "en";
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
				Locale.forLanguageTag(language));
	}
	
	/**
	 * Function returns an instance of LocalizationProvider.
	 * @return LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null)
            instance = new LocalizationProvider();
  
        return instance;
    }
	
	@Override
	public String getString(String key) {
		return this.bundle.getString(key);
	}
	@Override
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Function sets language and informs all listeners that the localization language has changed.
	 * @param language new localization language
	 */
	public void setLanguage(String language) {
		if (language != null) {
			this.language = language;
			this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
					Locale.forLanguageTag(language));
			
			this.fire();
		}
	}

}
