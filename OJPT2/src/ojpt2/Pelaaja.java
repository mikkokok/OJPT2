package ojpt2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ojpt2.server.PelinTila;
import ojpt2.server.RistinollaPalvelin;
import ojpt2.server.TicTacToeLogic;

public class Pelaaja extends UnicastRemoteObject implements PelaajaIF {

	private static final long serialVersionUID = 1L;
	
	private TicTacToeLogic peli;
	private String nimi;
	private boolean pelaakoViela;

	protected Pelaaja(TicTacToeLogic peli, String nimi) throws RemoteException {
		super();
		this.peli = peli;
		this.nimi = nimi;
		pelaakoViela = true;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		/*if (args.length < 2) {
			System.out.println("Anna pelaajan nimi");
			System.exit(0);
		}
		String pelaajaNimi = args[1].trim();
		
		RistinollaPalvelin peli = (RistinollaPalvelin) Naming.lookup("rmi://localhost/RistinollaPalvelin");*/

	}

	@Override
	public String getNimi() throws RemoteException {
		return nimi;
	}
	
	@Override
	public void otaVuoro() throws RemoteException {
		TicTacToeLogic.pelinTila = PelinTila.VUORO_KAYNNISSA;
	}
	
	@Override
	public void paataVuoro() throws RemoteException {
		TicTacToeLogic.pelinTila = PelinTila.VUORO_LOPPU;
	}

	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}

}
