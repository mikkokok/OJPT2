package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ojpt2.Pelaaja;

public class RistinollaPalvelin extends UnicastRemoteObject implements RistinollaPalvelinIF{

	private static final long serialVersionUID = 1L;
	private int peliID = 0;
	private HashMap<Integer, TicTacToeLogic> kaikkiPelit;
	
	protected RistinollaPalvelin() throws RemoteException {
		super();
		kaikkiPelit = new HashMap<Integer, TicTacToeLogic>();
	}

	@Override
	//Luodaan uusi peli ja lis‰t‰‰n listaan
	public void aloitaPeli() throws RemoteException {
		TicTacToeLogic peli = new  TicTacToeLogic();
		kaikkiPelit.put(peliID, peli);
		peliID += 1;
	}

	//Metodi joka palauttaa pelin annetun peliID:n mukaan
	public TicTacToeLogic getPeli(int peliID) throws RemoteException {
		
		TicTacToeLogic peli = null;
		
		for (Map.Entry<Integer, TicTacToeLogic> entry : kaikkiPelit.entrySet()) {
			int avain = entry.getKey();
			
			if(avain == peliID){
				peli = entry.getValue();
			}
		}
		return peli;
	}
	
	@Override
	//Metodi joka liitt‰‰ pelaajan viimeisimp‰‰n peliin
	public void liityPeliin(Pelaaja pelaaja) throws RemoteException {
		TicTacToeLogic peli = kaikkiPelit.get(kaikkiPelit.size());
		peli.lisaaPelaaja(pelaaja);
	}

	@Override
	//Metodi joka resetoi asiakkaan k‰yttˆliittym‰n
	public void resetGUI(TicTacToeLogic peli) throws RemoteException {
		peli.resetgame();	
	}

	@Override
	//Metodi joka poistaa pelaajan pelist‰
	public void poistaPelaaja(TicTacToeLogic peli, Pelaaja pelaaja) throws RemoteException {
		peli.poistaPelaaja(pelaaja);
	}

}
