package ojpt2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import ojpt2.server.RistinollaPalvelinIF;

/**
 * 
 * @author Ville Vahtera ja Mikko Kokkonen
 *
 */
public class PelaajaToteuttaja {

	/**
	 * 
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		RistinollaPalvelinIF ristinollaPalvelin = (RistinollaPalvelinIF) Naming.lookup("rmi://localhost/RistinollaPalvelin");	
		Pelaaja pelaaja = new Pelaaja(ristinollaPalvelin);
	}
}
