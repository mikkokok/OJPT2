package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RistinollaPalvelinIF extends Remote {
	void lisaaPelaaja(Pelaaja pelaaja) throws RemoteException;
	void aloitaPeli() throws RemoteException;
	void lopetaPeli() throws RemoteException;
	void setVuoro() throws RemoteException;
	void getTila() throws RemoteException;
	void resetPeli() throws RemoteException;
	void maaritaVoittaja() throws RemoteException;
}
