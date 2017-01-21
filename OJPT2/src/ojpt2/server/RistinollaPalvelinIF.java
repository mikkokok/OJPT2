package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ojpt2.Pelaaja;

public interface RistinollaPalvelinIF extends Remote {
	void rekisteroiPelaaja(Pelaaja pelaaja) throws RemoteException;
	void aloitaPeli(TicTacToeLogic peli) throws RemoteException;
	TicTacToeLogic getPeli(int peliID) throws RemoteException;
	void luoUusiPeli() throws RemoteException;
	int liityPeliin(Pelaaja pelaaja) throws RemoteException;
	void paivitaPelia(TicTacToeLogic peli) throws RemoteException;
	void poistaPeli(TicTacToeLogic peli) throws RemoteException;
	void resetGUI(TicTacToeLogic peli) throws RemoteException;
}
