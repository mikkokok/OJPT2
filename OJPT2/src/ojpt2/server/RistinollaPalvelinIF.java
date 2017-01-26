package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import ojpt2.PelaajaIF;

/**
 * @author Ville Vahtera ja Mikko Kokkonen
 */
public interface RistinollaPalvelinIF extends Remote {
	void rekisteroiPelaaja(PelaajaIF pelaaja) throws RemoteException;
	void aloitaPeli(TicTacToeLogic peli) throws RemoteException;
	TicTacToeLogic getPeli(int peliID) throws RemoteException;
	void luoUusiPeli() throws RemoteException;
	void liityPeliin(PelaajaIF pelaaja) throws RemoteException;
	void paivitaPelia(TicTacToeLogic peli) throws RemoteException;
	void keskeytaPeli(int peliID) throws RemoteException;
	void poistaPeli(TicTacToeLogic peli) throws RemoteException;
	void tarkistaVoitto(int peliID, int maxSiirrot) throws RemoteException;
}
