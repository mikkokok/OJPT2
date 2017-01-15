package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ojpt2.Pelaaja;

public interface RistinollaPalvelinIF extends Remote {
	void liityPeliin(Pelaaja pelaaja, String peliID) throws RemoteException;
	void aloitaPeli() throws RemoteException;
	void poistaPelaaja(Pelaaja pelaaja, String peliID) throws RemoteException;
	ArrayList<String> getKaikkiPelaajat() throws RemoteException;
	ArrayList<String> lopetaPeli(String idGame) throws RemoteException;
	void resetPeli(TicTacToeLogic peli) throws RemoteException;
}
