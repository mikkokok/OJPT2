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
 * @author Mikko Kokkonen ja Ville Vahtera
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
	private boolean debug = true; // debug rivit p‰‰lle/pois
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
	
	/**
	 * Metodi joka alustaa tekstilaatikot
	 */
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
	
	/**
	 * Metodi joka alustaa painikkeet
	 */
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
		buttonextrac = new JButton("");
		
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
		
		//Lis‰t‰‰n painikkeet ikkunaan ja samalla asetetaan ettei mit‰‰n
		//painiketta olla viel‰ klikattu
		for(int i = 0; i < painikkeet.length; i++){
			for(int j = 0; j < painikkeet[i].length; j++){
				window.add(painikkeet[i][j]);
				klikatutPainikkeet[i][j] = false;
			}
		}
		
		window.add(buttonextraa);
		window.add(buttonextrab);
		window.add(buttonextrac);

		// Luodaan nappuloille kuuntelijat
		buttonaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[0][0] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][0] = whichmark();
				if (debug)
					System.out.println("Paikka 0 0 "+whichmark());
				ChangeButton(buttonaa);
				buttonaa.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[0][1] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][1] = whichmark();
				if (debug)
					System.out.println("Paikka 0 1 "+whichmark());
				ChangeButton(buttonab);
				buttonab.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[0][2] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[0][2] = whichmark();
				if (debug)
					System.out.println("Paikka 0 2 "+whichmark());
				ChangeButton(buttonac);
				buttonac.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[1][0] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][0] = whichmark();
				if (debug)
					System.out.println("Paikka 1 0 "+whichmark());
				ChangeButton(buttonba);
				buttonba.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[1][1] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][1] = whichmark();
				if (debug)
					System.out.println("Paikka 1 1 "+whichmark());
				ChangeButton(buttonbb);
				buttonbb.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonbc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[1][2] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[1][2] = whichmark();
				if (debug)
					System.out.println("Paikka 1 2 "+whichmark());
				ChangeButton(buttonbc);
				buttonbc.setEnabled(false);
				buttoncount++;
			}          
		});
		buttonca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[2][0] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][0] = whichmark();
				if (debug)
					System.out.println("Paikka 2 0 "+whichmark());
				ChangeButton(buttonca);
				buttonca.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[2][1] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][1] = whichmark();
				if (debug)
					System.out.println("Paikka 2 1 "+whichmark());
				ChangeButton(buttoncb);
				buttoncb.setEnabled(false);
				buttoncount++;
			}          
		});
		buttoncc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				klikatutPainikkeet[2][2] = true;
				viimeisinSiirto = new String[3][3];
				viimeisinSiirto[2][2] = whichmark();
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
		buttonextraa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debug)
					System.err.println("Stopping GUI");
				System.exit(1);

			}          
		});
	}
	
	/**
	 * Metodi joka p‰ivitt‰‰ ensimm‰ist‰ tekstilaatikkoa
	 * @param text
	 */
	public void UpdateTextArea(String text) {
		textarea.append(text + "\n");
	}
	
	/**
	 * Metodi joka p‰ivitt‰‰ toista tekstilaatikkoa
	 * @param text
	 */
	public void UpdateTextAreab(String text) {
		textareab.setText(text);
	}
	
	/**
	 * Metodi joka muuttaa painikkeet tekstin joko X tai O
	 * @param button
	 */
	private void ChangeButton(JButton button) {
		if (oorx) {
			button.setText("O");
			oorx = false;
		}else {
			button.setText("X");
			oorx = true;
		}
	}
	/**
	 * Metodi joka ottaa k‰yttˆˆn ne painikkeet,
	 * joita ei olla viel‰ klikattu
	 */
	public void EnableButtons() {
		for(int i = 0; i < painikkeet.length; i++){
			for(int j = 0; j < painikkeet[i].length; j++){
				if(klikatutPainikkeet[i][j] == false){
					painikkeet[i][j].setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * Metodi joka poistaa k‰ytˆst‰ kaikki ne painikkeet,
	 * jotka eiv‰t ole viel‰ pois k‰ytˆst‰
	 */
	public void DisableButtons() {;
		for(int i = 0; i < painikkeet.length; i++){
			for(int j = 0; j < painikkeet[i].length; j++){
				if(klikatutPainikkeet[i][j] == false){
					painikkeet[i][j].setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * @return "X" tai "O"
	 */
	private String whichmark() {
		if (oorx) {
			return "O";
		} else {
			return "X";
		}
	}
	
	/**
	 * Metodi joka tekee vastustajan siirron pelaajan omaan GUI:hin
	 * @param peliTilanne
	 */
	public void teeVastustajanSiirto(String[][] peliTilanne){
			
		for(int i = 0; i < peliTilanne.length; i++){				
			for(int j = 0; j < peliTilanne[i].length; j++){		
				
				//Mik‰li kierroksessa oleva pelitilanteen alkio on joko X tai O ja alkiota vastaavan
				//painike on k‰ytˆss‰ niin painetaan kyseist‰ painiketta ja samalla resetoidaan viimeisin siirto
				//jottei se sekoita pelaajan ja vastustajan siirtoja
				if(peliTilanne[i][j].equals("X") || peliTilanne[i][j].equals("O") && painikkeet[i][j].isEnabled()){
					painikkeet[i][j].doClick();
					resetviimeisinSiirto();	
				}
			}	
		}
	}
	/**
	 * Metodi joka palauttaa tiedon pelaajan 
	 * viimeisimm‰st‰ siirrosta
	 * @return
	 */
	public String[][] getviimeisinSiirto(){
		return viimeisinSiirto;
	}
	
	/**
	 * Metodi joka resetoi pelaajan viimeisimm‰n siirron
	 */
	public void resetviimeisinSiirto(){
		viimeisinSiirto = null;
	}
	
	/**
	 * Metodi joka palauttaa pelaajan 
	 * siirtojen lukum‰‰r‰n
	 * @return
	 */
	public int getSiirtojenMaara(){
		return buttoncount;
	}
}
