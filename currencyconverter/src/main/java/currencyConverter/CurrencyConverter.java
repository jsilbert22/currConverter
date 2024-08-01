package currencyConverter;

import currencyConverter.frontend.MainWindow;

import java.awt.EventQueue;
import javax.swing.UIManager;

public class CurrencyConverter {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(() -> {
			try {
				// Create and show main window at startup
				MainWindow mainWindow = new MainWindow("en"); //set default to english
				mainWindow.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
