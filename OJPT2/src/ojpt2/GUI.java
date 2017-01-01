/**
 * 
 */
package ojpt2;

import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author Mikko Kokkonen
 *
 */
public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2778128863022518259L;
	private JFrame window;
	private static TextArea textarea;
	private static TextArea textareab;
	private static TextArea textareac;
	private JButton buttonaa; // vasemmalla ylh‰‰ll‰
	private JButton buttonab; // keskell‰ Ylh‰‰ll‰ 
	private JButton buttonac; // oikealla ylh‰‰ll‰ 
	private JButton buttonba; // vasemmalla keskirivi
	private JButton buttonbb; // keskell‰ keskirivi
	private JButton buttonbc; // oikealla keskirivi
	private JButton buttonca; // vasemmalla alarivi
	private JButton buttoncb; // keskell‰ alarivi
	private JButton buttoncc; // oikealla alarivi
	private JButton buttonextraa; // Kolme lis‰nappulaa
	private JButton buttonextrab;
	private JButton buttonextrac;
	private GridLayout manager = new GridLayout(5,3);


	public GUI () {
		window = new JFrame("Ristinolla");
		
		// M‰‰ritell‰‰n ikkuna
		window.setSize(900,900);
		window.setBackground(Color.BLUE);
		window.setTitle("Ristinolla");
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.setLayout(manager);
		window.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		manager.setHgap(5);
		manager.setVgap(5);
		
		// Hoida nappulat
		ButtonInit();
		// Tekstilaatikko
		TextAreaInit();

		
		
		UpdateTextArea("Game initialized");
	}
	private void ButtonInit() {
		// Luodaan nappulat ristinollaa varten
		buttonaa = new JButton("1");
		buttonab = new JButton("2");
		buttonac = new JButton("3");
		buttonba = new JButton("4");
		buttonbb = new JButton("5");
		buttonbc = new JButton("6");
		buttonca = new JButton("7");
		buttoncb = new JButton("8");
		buttoncc = new JButton("9");
		buttonextraa = new JButton("Extraa");
		buttonextrab = new JButton("Extrab");
		buttonextrac = new JButton("Extrac");
		
		// Nappulat ikkunan sis‰lle
		window.add(buttonaa);
		window.add(buttonab);
		window.add(buttonac);
		window.add(buttonba);
		window.add(buttonbb);
		window.add(buttonbc);
		window.add(buttonca);
		window.add(buttoncb);
		window.add(buttoncc);
		window.add(buttonextraa);
		window.add(buttonextrab);
		window.add(buttonextrac);
		
		// Luodaan nappuloille kuuntelijat

	}
	private void TextAreaInit() {
		textarea = new TextArea();
		textareab = new TextArea();
		textareac = new TextArea();
		textarea.setEditable(false);
		textareab.setEditable(false);
		window.add(textarea, BorderLayout.PAGE_END);
		window.add(textareab, BorderLayout.PAGE_END);
		window.add(textareac, BorderLayout.PAGE_END);
		
	}
	public static void UpdateTextArea(String text) {
		textarea.append(text + "\n");
	}
	public static void UpdateTextAreab(String text) {
		textareab.append(text + "\n");
	}

	
}
