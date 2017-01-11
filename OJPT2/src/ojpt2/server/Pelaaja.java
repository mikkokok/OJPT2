package ojpt2.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Pelaaja extends UnicastRemoteObject implements PelaajaIF {

	private static final long serialVersionUID = 1L;
	
	private RistinollaPalvelinIF peli;
	private String nimi;
	private boolean onkoMunVuoro;
	private boolean pelaakoViela;

	protected Pelaaja(RistinollaPalvelin peli, String nimi) throws RemoteException {
		super();
		this.peli = peli;
		this.nimi = nimi;
		onkoMunVuoro = false;
		pelaakoViela = true;
		peli.lisaaPelaaja(this);
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			System.out.println("Anna pelaajan nimi");
			System.exit(0);
		}
		String pelaajaNimi = args[1].trim();
		
		RistinollaPalvelin peli = (RistinollaPalvelin) Naming.lookup("rmi://localhost/RistinollaPalvelin");
		new Pelaaja(peli, pelaajaNimi);

	}

	@Override
	public String getNimi() throws RemoteException {
		return nimi;
	}

	@Override
	public void munVuoro() throws RemoteException {
		onkoMunVuoro = true;
	}
	
	@Override
	public void paataVuoro() throws RemoteException {
		onkoMunVuoro = false;
	}
	
	@Override
	public boolean getVuoro() throws RemoteException {
		return onkoMunVuoro;
	}

	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}

}
