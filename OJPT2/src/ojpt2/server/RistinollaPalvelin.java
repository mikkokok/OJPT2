package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ojpt2.Pelaaja;

public class RistinollaPalvelin extends UnicastRemoteObject implements RistinollaPalvelinIF{

	private static final long serialVersionUID = 1L;
	
	private Map<String, TicTacToeLogic> pelit;
	private Map<String, Pelaaja> pelaajat;
	
	protected RistinollaPalvelin() throws RemoteException {
		super();
		pelit = new HashMap<String,  TicTacToeLogic>();
		pelaajat = new HashMap<String, Pelaaja>();
	}

	@Override
	public void aloitaPeli() throws RemoteException {
		TicTacToeLogic peli = new  TicTacToeLogic();	
	}

	@Override
	public void resetPeli(TicTacToeLogic peli) throws RemoteException {
		peli.resetgame();	
	}

	@Override
	public void poistaPelaaja(Pelaaja pelaaja, String peliID) throws RemoteException {
		pelit.remove(peliID, pelaaja);
		
	}

	@Override
	public void liityPeliin(Pelaaja pelaaja, String peliID) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getKaikkiPelaajat() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> lopetaPeli(String idGame) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
