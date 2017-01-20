package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ojpt2.Pelaaja;

public interface PeliIF extends Remote {
	void lisaaPelaaja(Pelaaja pelaaja) throws RemoteException;
	void poistaPelaaja(Pelaaja pelaaja) throws RemoteException;
	void siirtoTehty(Pelaaja pelaaja) throws RemoteException;
	void maaritaVoittaja() throws RemoteException;
}
