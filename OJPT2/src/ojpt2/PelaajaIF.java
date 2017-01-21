package ojpt2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PelaajaIF extends Remote {
	void alustaGUI() throws RemoteException;
	void paivitaGUI() throws RemoteException;
	void otaVuoro() throws RemoteException; 
	void paataVuoro() throws RemoteException;
	void voitto() throws RemoteException;
	void resetMyGUI() throws RemoteException;
	int getPeliID() throws RemoteException;
	void vastaanOtaPeliTilanne(String[][] peliTilanne) throws RemoteException;
	String[][] lahetaPelinTilanne()throws RemoteException;
	void poistu() throws RemoteException;
}
