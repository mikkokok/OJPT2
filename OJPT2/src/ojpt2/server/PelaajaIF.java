package ojpt2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PelaajaIF extends Remote {
	String getNimi() throws RemoteException;
	void munVuoro() throws RemoteException; 
	void paataVuoro() throws RemoteException;
	boolean getVuoro() throws RemoteException;
	void poistu() throws RemoteException;
}
