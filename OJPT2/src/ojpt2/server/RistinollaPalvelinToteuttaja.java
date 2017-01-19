package ojpt2.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RistinollaPalvelinToteuttaja {

	private static boolean pelataanko = true;
	public static void main(String[] args) throws RemoteException, MalformedURLException {

		System.out.println("Aloitetaan uusi peli");
		Naming.rebind("RistinollaPalvelin", new RistinollaPalvelin());
	}
	/*
	 *rmic ojpt2.client.Pelaaja
	 *rmic ojpt2.server.RistinollaPalvelin
	 *rmiregistry
	 */
}
