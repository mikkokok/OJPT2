package ojpt2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import ojpt2.server.RistinollaPalvelinIF;

public class PelaajaToteuttaja {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		// TODO Auto-generated method stub
		RistinollaPalvelinIF ristinollaPalvelin = (RistinollaPalvelinIF) Naming.lookup("rmi://localhost/RistinollaPalvelin");	
		Pelaaja pelaaja = new Pelaaja(ristinollaPalvelin);
		Thread pelaajaSaie = new Thread(pelaaja);
		pelaajaSaie.start();
		System.out.println("Pelaajan luonti ja palvelimeen yhdist�minen onnistui");
	}

}
