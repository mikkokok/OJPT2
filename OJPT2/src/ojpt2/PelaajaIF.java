package ojpt2;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ojpt2.Pelaaja.VuoroTilanne;

/** 
 * @author Ville Vahtera ja Mikko Kokkonen
 */
public interface PelaajaIF extends Remote {
	VuoroTilanne getVuoroTilanne() throws RemoteException;
	boolean onkoVuoroKesken() throws RemoteException;
	void alustaGUI() throws RemoteException;
	void paivitaGUI() throws RemoteException;
	void otaVuoro() throws RemoteException; 
	void paataVuoro() throws RemoteException;
	void voitto() throws RemoteException;
	void havio() throws RemoteException;
	void tasapeli() throws RemoteException;
	int getPeliID() throws RemoteException;
	void vastaanOtaPeliTilanne(String[][] peliTilanne) throws RemoteException;
	String[][] lahetaViimeisinSiirtoni()throws RemoteException;
	void poistu() throws RemoteException;
}
