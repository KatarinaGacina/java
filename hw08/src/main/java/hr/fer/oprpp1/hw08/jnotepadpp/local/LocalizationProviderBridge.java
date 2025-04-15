package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Class which is a decorator for some other IlocalizationProvider.
 * @author Katarina Gacina
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	/**
	 * connection status
	 */
	private boolean connected = false;
	
	/**
	 * current language
	 */
	private String language;
	
	/**
	 * ILocalizationProvider 
	 */
	private ILocalizationProvider provider;
	
	/**
	 * Function updates connection status and connects object to LocalizationProvider and
	 * registers an instance of anonimous ILocalizationListener on the decorated object.
	 * Object cannot connect if it is already connected.
	 */
	public void connect() {
		if (connected) return;
		
		this.connected = true;
		
		this.provider = LocalizationProvider.getInstance();
		this.provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
			
		});
		
		if (language == null || !(language.equals(this.currentLanguage()))) {
			language = this.currentLanguage();
			
			this.fire();
		}
	}
	
	/**
	 * Function disconnects from the ILocalizationProvider, 
	 * ILocalizationProvider is deregistered from this object.
	 */
	public void disconnect() {
		this.connected = false;
		
		provider = null;
	}
	
	/**
	 * Function returns current language.
	 * @return current language
	 */
	private String currentLanguage() {
		return provider.getLanguage();
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getLanguage() {
		return language;
	}


	


}
