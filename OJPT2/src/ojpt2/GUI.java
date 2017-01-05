/**
 * 
 */
package ojpt2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private boolean oorx = true; // True aloitetaan O:lla
	private int buttoncount = 0;
	private TicTacToeLogic game;
	private int gamenumber = 1;
	private boolean debug = true; // debug rivit p‰‰lle/pois

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

		// Luodaan peli
		game = new TicTacToeLogic();
		UpdateTextArea("Peli alustettu");
		UpdateTextArea("----------------------");
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
		buttonextraa = new JButton("Start");
		buttonextrab = new JButton("Extrab");
		buttonextrac = new JButton("Reset");

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
		buttonaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(0, 0, whichmark());
				if (debug)
					System.out.println("Paikka 0 0 "+whichmark());
				ChangeButton(buttonaa);
				buttonaa.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(0, 1, whichmark());
				if (debug)
					System.out.println("Paikka 0 1 "+whichmark());
				ChangeButton(buttonab);
				buttonab.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(0, 2, whichmark());
				if (debug)
					System.out.println("Paikka 0 2 "+whichmark());
				ChangeButton(buttonac);
				buttonac.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(1, 0, whichmark());
				if (debug)
					System.out.println("Paikka 1 0 "+whichmark());
				ChangeButton(buttonba);
				buttonba.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(1, 1, whichmark());
				if (debug)
					System.out.println("Paikka 1 1 "+whichmark());
				ChangeButton(buttonbb);
				buttonbb.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonbc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(1, 2, whichmark());
				if (debug)
					System.out.println("Paikka 1 2 "+whichmark());
				ChangeButton(buttonbc);
				buttonbc.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(2, 0, whichmark());
				if (debug)
					System.out.println("Paikka 2 0 "+whichmark());
				ChangeButton(buttonca);
				buttonca.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(2, 1, whichmark());
				if (debug)
					System.out.println("Paikka 2 1 "+whichmark());
				ChangeButton(buttoncb);
				buttoncb.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.placemark(2, 2, whichmark());
				ChangeButton(buttoncc);
				buttoncc.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonextrac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnableButtons();
			}          
		});
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
	private void ChangeButton(JButton button) {
		CheckWin(); // Tarkistetaan voitto jokaisen nappulan painalluksen j‰lkeen
		if (oorx) {
			button.setText("O");
			oorx = false;
		}else {
			button.setText("X");
			oorx = true;
		}

	}
	private void EnableButtons() {
		buttonaa.setEnabled(true);
		buttonab.setEnabled(true);
		buttonac.setEnabled(true);
		buttonba.setEnabled(true);
		buttonbb.setEnabled(true);
		buttonbc.setEnabled(true);
		buttonca.setEnabled(true);
		buttoncb.setEnabled(true);
		buttoncc.setEnabled(true);
		buttonaa.setText("1");
		buttonab.setText("2");
		buttonac.setText("3");
		buttonba.setText("4");
		buttonbb.setText("5");
		buttonbc.setText("6");
		buttonca.setText("7");
		buttoncb.setText("8");
		buttoncc.setText("9");
		this.oorx = true; // O Aloittaa aina pelin
		game.resetgame();
		buttoncount = 0;
	}
	private void DisableButtons() {
		buttonaa.setEnabled(false);
		buttonab.setEnabled(false);
		buttonac.setEnabled(false);
		buttonba.setEnabled(false);
		buttonbb.setEnabled(false);
		buttonbc.setEnabled(false);
		buttonca.setEnabled(false);
		buttoncb.setEnabled(false);
		buttoncc.setEnabled(false);
		buttoncount = 0;
	}
	private void CheckWin() {
		if (game.isWin()[0].equalsIgnoreCase("YES")) { // Peli on voitettu
			if (game.isWin()[1].equalsIgnoreCase("X")) { // X on voittanut
				this.DisableButtons();
				UpdateTextArea("X voitti "+this.gamenumber+". pelin");
				this.gamenumber++;
			} else { // O on voittanut
				this.DisableButtons();
				UpdateTextArea("O voitti "+this.gamenumber+". pelin");
				this.gamenumber++;
			}
		}
	}
	private String whichmark() {
		if (oorx) {
			return "O";
		} else {
			return "X";
		}
	}


}
