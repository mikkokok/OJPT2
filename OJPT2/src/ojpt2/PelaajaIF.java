package ojpt2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PelaajaIF extends Remote {
	void paivitaPelia() throws RemoteException;
	void otaVuoro() throws RemoteException; 
	void paataVuoro() throws RemoteException;
	void voitto() throws RemoteException;
	void resetGUI() throws RemoteException;
	void poistu() throws RemoteException;
}
