/**
 * 
 */
package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ojpt2.Pelaaja;

/**
 * @author Mikko Kokkonen
 *
 */
public class TicTacToeLogic extends UnicastRemoteObject implements PeliIF {

	private static final long serialVersionUID = 1L;
	private String[][] game;
	private boolean xoro = true; // True jos O, false jos X
	private boolean debug = false; 
	
	private int peliID;
	private Pelaaja pelaaja1;
	private Pelaaja pelaaja2;
	private boolean peliOhi;
	public static PelinTila pelinTila;
	
	
	public TicTacToeLogic() throws RemoteException {
		super();
		game = new String[3][3]; // 3x3 peli
		// Täytä taulukko
		resetgame();
	
		pelaaja1 = null;
		pelaaja2 = null;
		
		peliOhi = false;
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
	
	@Override
	public void lisaaPelaaja(Pelaaja pelaaja) throws RemoteException {
		if(pelaaja1 == null){
			pelaaja1 = pelaaja;
			pelinTila = PelinTila.ODOTETAAN_TOISTA_PELAAJAA;
		}
		else if(pelaaja2 == null){
			pelaaja2 = pelaaja;
			pelinTila = PelinTila.PELIN_ALOITUS;
		}
		else{
			System.out.println("Peli on jo täynnä");
		}
	}

	@Override
	public void siirtoTehty(Pelaaja pelaaja) throws RemoteException {
		pelaaja.paataVuoro();
	}

	@Override
	public void maaritaVoittaja() throws RemoteException {
		
	}
	@Override
	public void poistaPelaaja(Pelaaja pelaaja) throws RemoteException {
		pelaaja.poistu();
		
	}
} // TicTacToeLogic 