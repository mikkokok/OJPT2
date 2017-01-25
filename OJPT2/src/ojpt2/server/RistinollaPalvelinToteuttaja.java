package ojpt2.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * 
 * @author Ville Vahtera ja Mikko Kokkonen
 *
 */
public class RistinollaPalvelinToteuttaja {

	/**
	 * 
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Naming.rebind("RistinollaPalvelin", new RistinollaPalvelin());
		System.out.println("RistinollaPalvelin käynnistetty");
	}

}
