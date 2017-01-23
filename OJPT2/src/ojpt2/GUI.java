/**
 * 
 */
package ojpt2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	//private TicTacToeLogic game;
	private int gamenumber = 1;
	private boolean debug = true; // debug rivit p‰‰lle/pois
	private boolean isRunning = true;
	private boolean empty = false;
	
	private boolean buttonaaClicked = false;
	private boolean buttonabClicked = false;
	private boolean buttonacClicked = false;
	private boolean buttonbaClicked = false;
	private boolean buttonbbClicked = false;
	private boolean buttonbcClicked = false;
	private boolean buttoncaClicked = false;
	private boolean buttoncbClicked = false;
	private boolean buttonccClicked = false;
	
	private JButton[][] painikkeet;
	private boolean[][] klikatutPainikkeet;
	private String[][] viimeisinSiirto;

	public GUI () {
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
		
		painikkeet = new JButton[3][3];
		klikatutPainikkeet = new boolean[3][3];

		// Hoida nappulat
		this.ButtonInit();
		// Tekstilaatikko
		this.TextAreaInit();
		
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
		
		//Tallenetaan nappuloiden sijainti muistiin
		//Painikkeiden sijainnit vastaavat ristinollan 3x3 taulukkoa
		painikkeet[0][0] = buttonaa;
		painikkeet[0][1] = buttonab;
		painikkeet[0][2] = buttonac;
		
		painikkeet[1][0] = buttonba;
		painikkeet[1][1] = buttonbb;
		painikkeet[1][2] = buttonbc;
		
		painikkeet[2][0] = buttonca;
		painikkeet[2][1] = buttoncb;
		painikkeet[2][2] = buttoncc;
		
		klikatutPainikkeet[0][0] = buttonaaClicked;
		klikatutPainikkeet[0][1] = buttonabClicked;
		klikatutPainikkeet[0][2] = buttonacClicked;
		
		klikatutPainikkeet[1][0] = buttonbaClicked;
		klikatutPainikkeet[1][1] = buttonbbClicked;
		klikatutPainikkeet[1][2] = buttonbcClicked;
		
		klikatutPainikkeet[2][0] = buttoncaClicked;
		klikatutPainikkeet[2][1] = buttoncbClicked;
		klikatutPainikkeet[2][2] = buttonccClicked;

		// Nappulat ikkunan sis‰lle
		/*window.add(buttonaa);
		window.add(buttonab);
		window.add(buttonac);
		window.add(buttonba);
		window.add(buttonbb);
		window.add(buttonbc);
		window.add(buttonca);
		window.add(buttoncb);
		window.add(buttoncc);*/
		
		window.add(painikkeet[0][0]);
		window.add(painikkeet[0][1]);
		window.add(painikkeet[0][2]);
		window.add(painikkeet[1][0]);
		window.add(painikkeet[1][1]);
		window.add(painikkeet[1][2]);
		window.add(painikkeet[2][0]);
		window.add(painikkeet[2][1]);
		window.add(painikkeet[2][2]);
		
		window.add(buttonextraa);
		window.add(buttonextrab);
		window.add(buttonextrac);

		// Luodaan nappuloille kuuntelijat
		buttonaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(0, 0, whichmark());
				if (debug)
					System.out.println("Paikka 0 0 "+whichmark());
				ChangeButton(buttonaa);
				buttonaaClicked = true;
				
				buttonaa.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][0] = whichmark();
				buttoncount++;
			}          
		});
		buttonab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(0, 1, whichmark());
				if (debug)
					System.out.println("Paikka 0 1 "+whichmark());
				ChangeButton(buttonab);
				buttonabClicked = true;
				
				buttonab.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][1] = whichmark();
				buttoncount++;
			}          
		});
		buttonac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(0, 2, whichmark());
				if (debug)
					System.out.println("Paikka 0 2 "+whichmark());
				ChangeButton(buttonac);
				buttonacClicked = true;
				
				buttonac.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][2] = whichmark();
				buttoncount++;
			}          
		});
		buttonba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(1, 0, whichmark());
				if (debug)
					System.out.println("Paikka 1 0 "+whichmark());
				ChangeButton(buttonba);
				buttonbaClicked = true;
				
				buttonba.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][0] = whichmark();
				buttoncount++;
			}          
		});
		buttonbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(1, 1, whichmark());
				if (debug)
					System.out.println("Paikka 1 1 "+whichmark());
				ChangeButton(buttonbb);
				buttonbbClicked = true;
				
				buttonbb.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][1] = whichmark();
				buttoncount++;
			}          
		});
		buttonbc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(1, 2, whichmark());
				if (debug)
					System.out.println("Paikka 1 2 "+whichmark());
				ChangeButton(buttonbc);
				buttonbcClicked = true;
				
				buttonbc.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][2] = whichmark();
				buttoncount++;
			}          
		});
		buttonca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(2, 0, whichmark());
				if (debug)
					System.out.println("Paikka 2 0 "+whichmark());
				ChangeButton(buttonca);
				buttoncaClicked = true;
				
				buttonca.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][0] = whichmark();
				buttoncount++;
			}          
		});
		buttoncb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(2, 1, whichmark());
				if (debug)
					System.out.println("Paikka 2 1 "+whichmark());
				ChangeButton(buttoncb);
				buttoncbClicked = true;
				
				buttoncb.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][1] = whichmark();
				buttoncount++;
			}          
		});
		buttoncc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//placexor(2, 2, whichmark());
				ChangeButton(buttoncc);
				buttonccClicked = true;

				buttoncc.setEnabled(false);
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][2] = whichmark();
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
		//this.game.placemark(row, column, xo);
	}
	private void ChangeButton(JButton button) {
		//this.CheckWin(); // Tarkistetaan voitto jokaisen nappulan painalluksen j‰lkeen
		if (oorx) {
			button.setText("O");
			oorx = false;
		}else {
			button.setText("X");
			oorx = true;
		}
	}
	public void EnableButtons() {
		
		for(int i = 0; i < painikkeet.length; i++){
			for(int j = 0; j < painikkeet[i].length; j++){
				if(klikatutPainikkeet[i][j] == false){
					painikkeet[i][j].setEnabled(true);
				}
			}
		}
		
		/*buttonaa.setEnabled(true);
		buttonab.setEnabled(true);
		buttonac.setEnabled(true);
		buttonba.setEnabled(true);
		buttonbb.setEnabled(true);
		buttonbc.setEnabled(true);
		buttonca.setEnabled(true);
		buttoncb.setEnabled(true);
		buttoncc.setEnabled(true);*/
	}
	public void DisableButtons() {
		
		for(int i = 0; i < painikkeet.length; i++){
			for(int j = 0; j < painikkeet[i].length; j++){
				if(klikatutPainikkeet[i][j] == false){
					painikkeet[i][j].setEnabled(false);
				}
				
			}
		}
		
		/*buttonaa.setEnabled(false);
		buttonab.setEnabled(false);
		buttonac.setEnabled(false);
		buttonba.setEnabled(false);
		buttonbb.setEnabled(false);
		buttonbc.setEnabled(false);
		buttonca.setEnabled(false);
		buttoncb.setEnabled(false);
		buttoncc.setEnabled(false);*/
	}
	public void ResetGUI() {
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
		buttoncount = 0;
	}
	/*private void CheckWin() {
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
	}*/
	private String whichmark() {
		if (oorx) {
			return "O";
		} else {
			return "X";
		}
	}
	
	/*public void setGame(TicTacToeLogic game){
		this.game = game;
	}*/
	
	public void teeVastustajanSiirto(String[][] peliTilanne){
			
		System.out.println("Tuli teeVastustajansiirto metodiin");
			
		for(int i = 0; i < peliTilanne.length; i++){
				
			for(int j = 0; j < peliTilanne[i].length; j++){
					
				System.out.println(peliTilanne[i][j] + " " + painikkeet[i][j].isEnabled());
					
				if(peliTilanne[i][j] == "X" || peliTilanne[i][j] == "O" && painikkeet[i][j].isEnabled()){
						
					painikkeet[i][j].doClick();
					System.out.println("Klikkasi painiketta");
						
				}
			}	
		}
	}
	
	public String[][] getviimeisinSiirto(){
		return viimeisinSiirto;
	}
	
	public void resetviimeisinSiirto(){
		viimeisinSiirto = null;
	}
	
	/*public static void main(String[] args) {
		pelit.add(new GUI());
	}*/
}
