package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that implements ILocalizationProvider interface and adds it the 
 * ability to register, de-register and inform (fire() method) listeners. 
 * @author Katarina Gacina
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/**
	 * ILocalizationListener set
	 */
	private Set<ILocalizationListener> listeners = new HashSet<>();
	
	/**
	 * Default constructor.
	 */
	public AbstractLocalizationProvider() {
		
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		if (listener != null && listeners.contains(listener)) {
			listeners.remove(listener);
		}
		
	}
	
	/**
	 * Function informs all listeners that localization language has changed.
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
