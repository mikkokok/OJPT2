/**
 * 
 */
package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import ojpt2.Pelaaja;
import ojpt2.Pelaaja.VuoroTilanne;
import ojpt2.PelaajaIF;
import ojpt2.server.TicTacToeLogic.PelinTila;

/**
 * @author Mikko Kokkonen
 *
 */
public class TicTacToeLogic extends UnicastRemoteObject implements Runnable {

	private static final long serialVersionUID = 1L;
	private String[][] game;
	private boolean xoro = true; // True jos O, false jos X
	private boolean debug = false; 
	
	private RistinollaPalvelin ristinollaPalvelin;
	private PelaajaIF pelaaja1;
	private PelaajaIF pelaaja2;
	private boolean peliKaynnissa;
	
	public enum PelinTila {
		EI_PELAAJIA,
		ODOTETAAN_TOISTA_PELAAJAA,
		PELIN_ALOITUS,
		PELI_KAYNNISSA,
		ERAN_LOPPU,
		PELI_OHI
	}
	
	public PelinTila pelinTila;
	
	public TicTacToeLogic(RistinollaPalvelin ristinollaPalvelin) throws RemoteException {
		super();
		game = new String[3][3]; // 3x3 peli
		// Täytä taulukko
		resetgame();
	
		this.ristinollaPalvelin = ristinollaPalvelin;
		pelaaja1 = null;
		pelaaja2 = null;
		
		peliKaynnissa = false;
		pelinTila = PelinTila.EI_PELAAJIA;
		
	} // Konstruktori
	public void resetgame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game[i][j] = "M";
			}
		}
	}
	public void placemark(int row, int column, String xo) {
		game[row][column] = xo;
	} // placemark
	public String[] isWin() {
		String[] winning = new String[2];
		winning[0] = "NO";
		winning[1] = "XO";
		if (checkrows("X") || checkcolumns("X") || checkcornertocorner("X")) {
			winning[0] = "YES";
			winning[1] = "X";
			if (debug) {
				printgame();
			}

		} 
		if (checkrows("O") || checkcolumns("O") || checkcornertocorner("O")) {
			winning[0] = "YES";
			winning[1] = "O";
			if (debug) {
				printgame();	
			}

		}
		return winning;
	}
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
	private void printgame() { // For debugging
		for (int i = 0; i < 3; i++) {
			System.out.println("");
			for (int j = 0; j < 3; j++) {
				System.out.print(game[i][j]+" ");
			}
		}
		System.out.println("");
	}
	
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
			System.out.println("Peli on jo täynnä."); //Debuggausta varten, ei pitäisi koskaan tulostaa tätä
		}
	}
	
	public String[][] getPeliTilanne(){
		return game;
	}
	
	public void lisaaSiirto(String[][] peliTilanne){
		for(int i = 0; i < this.game.length; i++){
			for(int j = 0; j < this.game[i].length; j++){
				if(peliTilanne[i][j] != null){
					placemark(i, j, peliTilanne[i][j]);
				}
			}
		}
		//this.game = game;
	}
	
	public int getPelaajienMaara(){
		if(pelaaja1 == null)
			return 0;
		else if(pelaaja2 == null)
			return 1;
		else 
			return 2;
	}
	
	public PelaajaIF getPelaaja1(){
		return pelaaja1;
	}
	
	public PelaajaIF getPelaaja2(){
		return pelaaja2;
	}
	
	public boolean getPeliKaynnissa(){
		return peliKaynnissa;
	}
	
	public PelinTila getPelinTila(){
		return pelinTila;
	}
	
	public void aloitaPeli(){
		peliKaynnissa = true;
		System.out.println("Tuli Peli-luokan aloitaPeli-metodiin");
		run();
	}
	
	public void lopetaPeli(){
		peliKaynnissa = false;
	}
	
	@Override
	public void run() {
		try {
			ristinollaPalvelin.paivitaPelia(this);
		} catch (RemoteException e) {
			e.printStackTrace();

		}

	}
}// TicTacToeLogic 