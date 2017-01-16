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
	private GUI gui;
	private boolean pelaakoViela;
	private int voitot = 0;
	
	private enum VuoroTilanne{
		VUOROJA_EI_JAETTU,
		MUN_VUORO,
		VASTUSTAJAN_VUORO
	}
	
	private VuoroTilanne vuoroTilanne;

	protected Pelaaja(TicTacToeLogic peli) throws RemoteException {
		super();
		this.peli = peli;	
		gui = new GUI();
		pelaakoViela = true;
		vuoroTilanne = VuoroTilanne.VUOROJA_EI_JAETTU;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub			
		RistinollaPalvelin peli = (RistinollaPalvelin) Naming.lookup("rmi://localhost/RistinollaPalvelin");	
		peli.aloitaPeli();

	}
	
	@Override
	public void otaVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.MUN_VUORO;
		gui.EnableButtons();
	}
	
	@Override
	public void paataVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.VASTUSTAJAN_VUORO;
		gui.DisableButtons();
	}
	
	@Override
	public void voitto() throws RemoteException {
		voitot += 1;
	}

	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}

}
