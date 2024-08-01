package currencyConverter.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ResourceBundle;

public class AboutWindow extends JFrame {
    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("translation"); //$NON-NLS-1$
    private JPanel contentPane;
    private static AboutWindow windowInstance = null;

    /**
     * Create the aboutWindow frame
     */
    private AboutWindow(String language) {
        BUNDLE = ResourceBundle.getBundle("translation_" + language);
        setTitle(BUNDLE.getString("AboutWindow.this.title")); //$NON-NLS-1$
        setSize(450, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        // Window components
        contentPane = new JPanel(null); // Use null layout
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Label "author"
        JLabel lblAuthor = new JLabel(BUNDLE.getString("AboutWindow.lblAuthor.text"));
        lblAuthor.setBounds(25, 120, 219, 33);
        contentPane.add(lblAuthor);

        // label "instructions"
        JLabel lblInstructions = new JLabel(BUNDLE.getString("AboutWindow.lblInstructions"));
        lblInstructions.setBounds(25, 15, 400, 100);
        contentPane.add(lblInstructions);

        // label with a clickable link
        JLabel lblLink = new JLabel(BUNDLE.getString("AboutWindow.github"));
        lblLink.setBounds(25, 150, 219, 33);
        lblLink.setForeground(Color.BLUE);
        lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/UCM-FDI-DISIA/project-group-repository-currencyconverter"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(lblLink);
    }

    // Check if the window is already created to launch only one instance of the window.
    public static AboutWindow getInstance(String language) {
        windowInstance = new AboutWindow(language);
        return windowInstance;
    }
}
