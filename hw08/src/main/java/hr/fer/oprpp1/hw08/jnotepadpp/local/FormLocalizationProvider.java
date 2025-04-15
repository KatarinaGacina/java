package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class derived from LocalizationProviderBridge; in its constructor it registeres itself as a WindowListener to its JFrame
 * @author Katarina Gacina
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor.
	 * It registeres object as a WindowListener to its JFrame; when frame is opened, 
	 * it calls connect and when frame is closed, it calls disconnect.
	 * @param provider ILocalizationProvider
	 * @param frame JFrame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {	
		FormLocalizationProvider.this.connect();
		
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				FormLocalizationProvider.this.connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				FormLocalizationProvider.this.disconnect();
			}

		});
	}
	
}
