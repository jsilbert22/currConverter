package currencyConverter.frontend;

import currencyConverter.backend.FetchRate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("translation"); //$NON-NLS-1$
	private static final ResourceBundle CURRENCIES = ResourceBundle.getBundle("currencies");
	private JPanel contentPane;
	private JTextField fieldAmount;

	/**
	 * Create the mainWindow frame
	 * @param language language bundle
	 */
	public MainWindow(String language) {
		BUNDLE = ResourceBundle.getBundle("translation_"+language);
		setTitle(BUNDLE.getString("MainWindow.this.title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 300);
		setLocationRelativeTo(null);
		setResizable( false );
		
		// Create menu bar
		setJMenuBar(new MenuBar(this, language));

		// Window components
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Label "Convert"
		JLabel lblConvert = new JLabel(BUNDLE.getString("MainWindow.lblConvert.text")); //$NON-NLS-1$
		lblConvert.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConvert.setBounds(0, 14, 92, 15);
		contentPane.add(lblConvert);
	
		// ComboBox of the first currency
		final JComboBox<String> comboBoxCountry1 = new JComboBox<>();
		comboBoxCountry1.setBounds(147, 7, 240, 28);
		populate(comboBoxCountry1);
		contentPane.add(comboBoxCountry1);
		
		// Label "To"
		JLabel lblTo = new JLabel(BUNDLE.getString("MainWindow.lblTo.text")); //$NON-NLS-1$
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(66, 54, 26, 15);
		contentPane.add(lblTo);
		
		// ComboBox of the second currency
		final JComboBox<String> comboBoxCountry2 = new JComboBox<>();
		comboBoxCountry2.setBounds(147, 47, 240, 28);
		populate(comboBoxCountry2);
		contentPane.add(comboBoxCountry2);
		
		// Label "Amount"
		final JLabel lblAmount = new JLabel(BUNDLE.getString("MainWindow.lblAmount.text")); //$NON-NLS-1$
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(23, 108, 69, 15);
		contentPane.add(lblAmount);
		
		// Textfield where the user 
		fieldAmount = new JTextField();
		fieldAmount.setText("0.00");
		fieldAmount.setBounds(147, 101, 103, 29);
		contentPane.add(fieldAmount);
		fieldAmount.setColumns(10);
		fieldAmount.setDocument(new JTextFieldLimit(8));
     	
		// Label displaying result of conversion
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.LEFT);
		lblResult.setBounds(147, 188, 428, 38);
		contentPane.add(lblResult);
		
		// Button "Convert"
		JButton btnConvert = new JButton(BUNDLE.getString("MainWindow.btnConvert.text")); //$NON-NLS-1$
		btnConvert.setBounds(147, 142, 129, 38);
		btnConvert.addActionListener(arg0 -> {
			String nameCurrency1 = comboBoxCountry1.getSelectedItem().toString();
			String nameCurrency2 = comboBoxCountry2.getSelectedItem().toString();

			String result = convertBtnAction(nameCurrency1,nameCurrency2);

			lblResult.setText(result);
		});
		contentPane.add(btnConvert);
	}

	/**
	 * Helper function for convert button action
	 * @param currency1 start currency
	 * @param currency2 end currency
	 * @return Print result for the converted values
	 */
	private String convertBtnAction(String currency1, String currency2 ){
		String result;
		String formattedPrice;
		String formattedAmount;
		Double price;
		Double amount;
		DecimalFormat format = new DecimalFormat("#0.00");

		try {
			amount = Double.parseDouble( fieldAmount.getText() ) ;
			price = convert(currency1, currency2, amount);

			// Format numbers to avoid "E7" problem
			formattedPrice = format.format(price);
			formattedAmount = format.format(amount);

			result = formattedAmount + " " + currency1 + " = " + formattedPrice + " " + currency2;
		} catch (NumberFormatException e) {
			//given invalid conversion
			e.printStackTrace();
			result = BUNDLE.getString("MainWindow.result.error");
		}

		return result;
	}

	/**
	 * Fill comboBox with currencies name
	 * @param comboBox dropdown box
	 */
	public static void populate(JComboBox<String> comboBox) {
		//sort currencies alphabetically
		TreeSet<String> orderedKeys = new TreeSet<>(CURRENCIES.keySet());

		// Populate cells
		for (String cur: orderedKeys){
			String displayName = CURRENCIES.getString(cur) + " (" + cur + ")";

			comboBox.addItem(displayName);
		}
	}

	/**
	 * Find the short name and the exchange value of the second currency
	 * @param currency1 starting currency
	 * @param currency2 target currency
	 * @param amount to convert
	 * @return the converted amount
	 */
	public static Double convert(String currency1, String currency2, Double amount) {

		// Get Currency Codes from the display name
		String curr1Code = currency1.substring(currency1.length()-4,currency1.length()-1);
		String curr2Code = currency2.substring(currency2.length()-4,currency2.length()-1);

		// Create new FetchRate Object and call API
		FetchRate newRate = new FetchRate(curr1Code,curr2Code,amount);
		return newRate.getRate();
	}
}