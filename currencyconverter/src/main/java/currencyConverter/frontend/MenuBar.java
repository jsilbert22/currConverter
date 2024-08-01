package currencyConverter.frontend;

import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

public class MenuBar extends JMenuBar{

    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("translation"); //$NON-NLS-1$
    private JFrame currentWindow;
    private String language;
    public MenuBar(JFrame currWindow, String language){
        this.language = language;
        BUNDLE = ResourceBundle.getBundle("translation_"+language);
        this.currentWindow = currWindow;
        fileMenuInnit();
        helpMenuInnit();
        languageMenuInnit();
    }

    /**
     * initialize file menu
     */
    private void fileMenuInnit(){
        // "File" menu
        JMenu mnFile = new JMenu(BUNDLE.getString("MainWindow.mnFile.text")); //$NON-NLS-1$
        mnFile.setMnemonic(KeyEvent.VK_F);
        this.add(mnFile);

        // "Quit" menu item
        JMenuItem mntmQuit = new JMenuItem(BUNDLE.getString("MainWindow.mntmQuit.text")); //$NON-NLS-1$
        mntmQuit.setMnemonic(KeyEvent.VK_Q);
        mntmQuit.addActionListener(arg0 -> System.exit(0));
        mnFile.add(mntmQuit);
    }

    /**
     * initialize help menu
     */
    private void helpMenuInnit(){
        // "Help" menu
        JMenu mnHelp = new JMenu(BUNDLE.getString("MainWindow.mnHelp.text")); //$NON-NLS-1$
        mnHelp.setMnemonic(KeyEvent.VK_H);
        this.add(mnHelp);

        // "About" menu item
        JMenuItem mntmAbout = new JMenuItem(BUNDLE.getString("MainWindow.mntmAbout.text"));		 //$NON-NLS-1$
        mntmAbout.addActionListener(arg0 -> EventQueue.invokeLater(() -> {
            try {
                AboutWindow.getInstance(language).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        mntmAbout.setMnemonic(KeyEvent.VK_A);
        mnHelp.add(mntmAbout);
    }

    /**
     * Initialize langauge menu
     */
    private void languageMenuInnit(){
        // "Language" menu
        JMenu mnLng = new JMenu(BUNDLE.getString("MainWindow.mnLanguage.text"));
        this.add(mnLng);

        // Language menu items
        JMenuItem mntmEn = new JMenuItem(BUNDLE.getString("MainWindow.mntmEn.text"));
        JMenuItem mntmFr = new JMenuItem(BUNDLE.getString("MainWindow.mntmFr.text"));
        JMenuItem mntmSp = new JMenuItem(BUNDLE.getString("MainWindow.mntmSp.text"));

        mnLng.add(mntmEn);
        mnLng.add(mntmFr);
        mnLng.add(mntmSp);

        mntmEn.addActionListener(e-> openNewLanguageWindow("en"));
        mntmFr.addActionListener(e -> openNewLanguageWindow("fr"));
        mntmSp.addActionListener(e -> openNewLanguageWindow("sp"));
    }

    private void openNewLanguageWindow(String language){
        currentWindow.setVisible(false);
        MainWindow mainWindow = new MainWindow(language);
        mainWindow.setVisible(true);
    }


}
