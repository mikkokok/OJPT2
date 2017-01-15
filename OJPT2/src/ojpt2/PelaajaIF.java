package ojpt2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PelaajaIF extends Remote {
	String getNimi() throws RemoteException;
	void otaVuoro() throws RemoteException; 
	void paataVuoro() throws RemoteException;
	void poistu() throws RemoteException;
}
