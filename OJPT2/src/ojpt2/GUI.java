/**
 * 
 */
package ojpt2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;

import ojpt2.server.TicTacToeLogic;

/**
 * @author Mikko Kokkonen
 *
 */
public class GUI extends Thread {
	private JFrame window;
	private TextArea textarea;
	private TextArea textareab;
	private TextArea textareac;
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
	private boolean isRunning = true;
	private boolean empty = false;
	
	private static LinkedList<GUI> pelit = new LinkedList<GUI>();

	public GUI () {
		Thread tredi = this;
		tredi.start();
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
		buttonextraa = new JButton("Stop");
		buttonextrab = new JButton("");
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
				placexor(0, 1, whichmark());
				if (debug)
					System.out.println("Paikka 0 1 "+whichmark());
				ChangeButton(buttonab);
				buttonab.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placexor(0, 2, whichmark());
				if (debug)
					System.out.println("Paikka 0 2 "+whichmark());
				ChangeButton(buttonac);
				buttonac.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placexor(1, 0, whichmark());
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
				placexor(1, 2, whichmark());
				if (debug)
					System.out.println("Paikka 1 2 "+whichmark());
				ChangeButton(buttonbc);
				buttonbc.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placexor(2, 0, whichmark());
				if (debug)
					System.out.println("Paikka 2 0 "+whichmark());
				ChangeButton(buttonca);
				buttonca.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placexor(2, 1, whichmark());
				if (debug)
					System.out.println("Paikka 2 1 "+whichmark());
				ChangeButton(buttoncb);
				buttoncb.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				placexor(2, 2, whichmark());
				ChangeButton(buttoncc);
				buttoncc.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonextrac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnableButtons();
				ResetGUI();
			}          
		});
		buttonextraa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//isRunning = false;
				if (debug)
					System.err.println("Stopping GUI");
				System.exit(1);

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
	public void UpdateTextArea(String text) {
		textarea.append(text + "\n");
	}
	public void UpdateTextAreab(String text) {
		textareab.append(text + "\n");
	}
	public void placexor(int row, int column, String xo) {
		this.game.placemark(row, column, xo);
	}
	private void ChangeButton(JButton button) {
		this.CheckWin(); // Tarkistetaan voitto jokaisen nappulan painalluksen j‰lkeen
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
	}
	private void ResetGUI() {
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
		this.game.resetgame();
		buttoncount = 0;
	}
	private void CheckWin() {
		if (this.game.isWin()[0].equalsIgnoreCase("YES")) { // Peli on voitettu
			if (this.game.isWin()[1].equalsIgnoreCase("X")) { // X on voittanut
				this.DisableButtons();
				this.UpdateTextArea("X voitti "+this.gamenumber+". pelin");
				this.gamenumber++;
			} else { // O on voittanut
				this.DisableButtons();
				this.UpdateTextArea("O voitti "+this.gamenumber+". pelin");
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
	public void run() {
		this.window = new JFrame("Ristinolla");

		// M‰‰ritell‰‰n ikkuna
		this.window.setSize(900,900);
		this.window.setBackground(Color.BLUE);
		this.window.setTitle("Ristinolla");
		this.window.setLocationRelativeTo(null);
		this.window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.window.setVisible(true);
		this.window.setResizable(false);
		this.window.setLayout(manager);
		this.window.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.manager.setHgap(5);
		this.manager.setVgap(5);

		// Hoida nappulat
		this.ButtonInit();
		// Tekstilaatikko
		this.TextAreaInit();

		// Luodaan peli
		try {
			this.game = new TicTacToeLogic();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.UpdateTextArea("Peli alustettu");
		this.UpdateTextArea("----------------------");
	}
	

	public static void main(String[] args) {

		pelit.add(new GUI());
	}
}
