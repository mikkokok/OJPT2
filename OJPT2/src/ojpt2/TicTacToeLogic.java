/**
 * 
 */
package ojpt2;

/**
 * @author Mikko Kokkonen
 *
 */
public class TicTacToeLogic {

	private String[][][] game;
	private boolean xoro = true; // True jos O, false jos X
	public TicTacToeLogic() {
		game = new String[3][3][3]; // 3x3 peli
	} // Konstruktori
	
	public void placemark() {
		
	} // asetamerkki
	public String[] isWin() {
		String[] winning = new String[2];
		winning[0] = "YES";
		winning[1] = "X";
		
		return winning;
	}
	
	
} // TicTacToeLogic 