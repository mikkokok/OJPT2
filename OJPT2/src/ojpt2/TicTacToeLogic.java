/**
 * 
 */
package ojpt2;

/**
 * @author Mikko Kokkonen
 *
 */
public class TicTacToeLogic {

	private String[][] game;
	private boolean xoro = true; // True jos O, false jos X
	private boolean debug = false; 
	public TicTacToeLogic() {
		game = new String[3][3]; // 3x3 peli
		// Täytä taulukko
		resetgame();
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
} // TicTacToeLogic 