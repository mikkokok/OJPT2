package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ojpt2.client.Pelaaja;

public interface RistinollaPalvelinIF extends Remote {
	void aloitaPeli() throws RemoteException;
	TicTacToeLogic getPeli(int peliID) throws RemoteException;
	void liityPeliin(Pelaaja pelaaja) throws RemoteException;
	void poistaPelaaja(TicTacToeLogic peli, Pelaaja pelaaja) throws RemoteException;
	void resetGUI(TicTacToeLogic peli) throws RemoteException;
}
