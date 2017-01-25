/**
 * 
 */
package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ojpt2.PelaajaIF;

/**
 * @author Mikko Kokkonen ja Ville Vahtera
 *
 */
public class TicTacToeLogic extends UnicastRemoteObject implements Runnable {

	private static final long serialVersionUID = 1L;
	private String[][] game;
	private boolean debug = false; 
	
	private RistinollaPalvelin ristinollaPalvelin;
	private PelaajaIF pelaaja1;
	private PelaajaIF pelaaja2;
	private boolean peliKaynnissa;
	private boolean pelaaja1Aloitus; //true jos pelaaja1 aloittaa pelin ja false jos pelaaja2 aloittaa
	
	//Pelin mahdolliset tilat
	public enum PelinTila {
		EI_PELAAJIA,
		ODOTETAAN_TOISTA_PELAAJAA,
		PELIN_ALOITUS,
		PELI_KAYNNISSA,
		ERAN_LOPPU,
		PELI_OHI
	}
	
	public PelinTila pelinTila; //Muuttuja joka m‰‰ritt‰‰ miss‰ tilassa peli on
	
	/**
	 * 
	 * @param ristinollaPalvelin
	 * @throws RemoteException
	 */
	public TicTacToeLogic(RistinollaPalvelin ristinollaPalvelin) throws RemoteException {
		super();
		game = new String[3][3]; // 3x3 peli
		// T‰yt‰ taulukko
		resetgame();
	
		this.ristinollaPalvelin = ristinollaPalvelin;
		pelaaja1 = null;
		pelaaja2 = null;
		
		peliKaynnissa = false;
		pelinTila = PelinTila.EI_PELAAJIA;
		
	} // Konstruktori
	
	/**
	 * Metodi joka resetoi pelin tilanteen
	 */
	public void resetgame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game[i][j] = "M";
			}
		}
	}
	
	/**
	 * Metodi joka lis‰‰ siirron pelitilanteeseen
	 * @param row
	 * @param column
	 * @param xo
	 */
	public void placemark(int row, int column, String xo) {
		game[row][column] = xo;
	} // placemark
	
	/**
	 * Metodi joka tarkistaa onko peli voitettu
	 * @return
	 */
	public String isWin() {
		String winner = "none";

		if (checkrows("X") || checkcolumns("X") || checkcornertocorner("X")) {
			winner = "X";
			if (debug) {
				printgame();
			}

		} 
		if (checkrows("O") || checkcolumns("O") || checkcornertocorner("O")) {
			winner = "O";
			if (debug) {
				printgame();	
			}

		}
		return winner;
	}
	
	/**
	 * Metodi joka tarkistaa onko vaakariveiss‰ on 3 X:‰‰ tai O:ta
	 * @param xo
	 * @return
	 */
	private boolean checkrows(String xo) {
		boolean result = false;
		if (game[0][0].equalsIgnoreCase(xo) && game[0][1].equalsIgnoreCase(xo) && game[0][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		if (game[1][0].equalsIgnoreCase(xo) && game[1][1].equalsIgnoreCase(xo) && game[1][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		if (game[2][0].equalsIgnoreCase(xo) && game[2][1].equalsIgnoreCase(xo) && game[2][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Metodi joka tarkistaa onko pystyriveiss‰ 3 X:‰‰ tai O:ta
	 * @param xo
	 * @return
	 */
	private boolean checkcolumns(String xo) {
		boolean result = false;
		if (game[0][0].equalsIgnoreCase(xo) && game[1][0].equalsIgnoreCase(xo) && game[2][0].equalsIgnoreCase(xo)) {
			result = true;
		}
		if (game[0][1].equalsIgnoreCase(xo) && game[1][1].equalsIgnoreCase(xo) && game[2][1].equalsIgnoreCase(xo)) {
			result = true;
		}
		if (game[0][2].equalsIgnoreCase(xo) && game[1][2].equalsIgnoreCase(xo) && game[2][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Metodi joka tarkistaa onko kulmista kulmaan 3 X:‰‰ tai O:ta
	 * @param xo
	 * @return
	 */
	private boolean checkcornertocorner(String xo) {
		boolean result = false;
		if (game[0][0].equalsIgnoreCase(xo) && game[1][1].equalsIgnoreCase(xo) && game[2][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		if (game[2][0].equalsIgnoreCase(xo) && game[1][1].equalsIgnoreCase(xo) && game[0][2].equalsIgnoreCase(xo)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Metodi joka tulostaa pelintilanteen
	 */
	private void printgame() { // For debugging
		for (int i = 0; i < 3; i++) {
			System.out.println("");
			for (int j = 0; j < 3; j++) {
				System.out.print(game[i][j]+" ");
			}
		}
		System.out.println("");
	}
	
	/**
	 * Metodi joka lis‰‰ pelaaja peliin ja vaihtaa pelin tilaa
	 * @param pelaaja
	 */
	public void lisaaPelaaja(PelaajaIF pelaaja) {
		if(pelaaja1 == null){
			pelaaja1 = pelaaja;
			pelinTila = PelinTila.ODOTETAAN_TOISTA_PELAAJAA;
		}
		else if(pelaaja2 == null){
			pelaaja2 = pelaaja;
			pelinTila = PelinTila.PELIN_ALOITUS;
		}
		else{
			System.out.println("Peli on jo t‰ynn‰."); //Debuggausta varten, ei pit‰isi koskaan tulostaa t‰t‰
		}
	}
	
	/**
	 * Metodi joka asettaa pelille sen kumpi pelaaja aloitti
	 * @param pelaaja1Aloitus
	 */
	public void setKumpiAloitti(boolean pelaaja1Aloitus){
		if(pelaaja1Aloitus){
			this.pelaaja1Aloitus = true;
		}
		else
			this.pelaaja1Aloitus = false;
	}
	
	/**
	 * Metodi joka palauttaa tiedon siit‰,
	 * kumpi pelaaja aloitti 
	 * @return true jos pelaaja1 aloitti
	 */
	public boolean AloittikoPelaaja1(){
		return this.pelaaja1Aloitus;
	}
	
	/**
	 * Metodi joka palauttaa pelin tilanteen
	 * @return
	 */
	public String[][] getPeliTilanne(){
		return game;
	}
	
	/**
	 * Metodi joka lis‰‰ siirron pelitilanteeseen
	 * @param peliTilanne
	 */
	public void lisaaSiirto(String[][] viimeisinSiirto){
		for(int i = 0; i < this.game.length; i++){
			for(int j = 0; j < this.game[i].length; j++){
				if(viimeisinSiirto[i][j] != null){
					placemark(i, j, viimeisinSiirto[i][j]);
				}
			}
		}
	}
	
	/**
	 * Metodi joka palauttaa pelaajien lukum‰‰r‰n
	 * @return
	 */
	public int getPelaajienMaara(){
		if(pelaaja1 == null)
			return 0;
		else if(pelaaja2 == null)
			return 1;
		else 
			return 2;
	}
	
	/**
	 * Metodi joka palauttaa pelaaja1:sen
	 * @return
	 */
	public PelaajaIF getPelaaja1(){
		return pelaaja1;
	}
	
	/**
	 * Metodi joka palauttaa pelaaja2:sen
	 * @return
	 */
	public PelaajaIF getPelaaja2(){
		return pelaaja2;
	}
	
	/**
	 * Metodi joka palauttaa tiedon siit‰
	 * onko peli k‰ynniss‰
	 * @return
	 */
	public boolean getPeliKaynnissa(){
		return peliKaynnissa;
	}
	
	/**
	 * Metodi joka palauttaa pelin tilan
	 * @return
	 */
	public PelinTila getPelinTila(){
		return pelinTila;
	}
	
	/**
	 * Metodi joka k‰ynnist‰‰ pelin
	 */
	public void aloitaPeli(){
		peliKaynnissa = true;
		run();
	}
	
	/**
	 * Metodi joka lopettaa pelin
	 */
	public void lopetaPeli(){
		peliKaynnissa = false;
	}
	
	/**
	 * run-metodi, jossa kutsutaan palvelinta p‰ivitt‰m‰‰n peli‰ niin 
	 * kauan kun peli on k‰ynniss‰ ja jos peli ei ole k‰ynniss‰ niin
	 * pyydet‰‰n palvelinta poistamaan peli
	 */
	@Override
	public void run() {
		while(peliKaynnissa){
			try {
				ristinollaPalvelin.paivitaPelia(this);
			} catch (RemoteException e) {
				e.printStackTrace();

			}
		}
		
		try {
			ristinollaPalvelin.poistaPeli(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}// TicTacToeLogic 