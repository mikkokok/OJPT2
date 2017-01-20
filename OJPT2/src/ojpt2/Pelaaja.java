package ojpt2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ojpt2.server.PelinTila;
import ojpt2.server.RistinollaPalvelin;
import ojpt2.server.TicTacToeLogic;

public class Pelaaja extends UnicastRemoteObject implements PelaajaIF, Runnable {

	private static final long serialVersionUID = 1L;
	
	private TicTacToeLogic peli;
	private GUI gui;
	private RistinollaPalvelin ristinollaPalvelin;
	private boolean pelaakoViela;
	private int voitot = 0;
	private int virheet = 0;
	
	private enum VuoroTilanne{
		VUOROJA_EI_JAETTU,
		MUN_VUORO,
		VASTUSTAJAN_VUORO
	}
	
	private VuoroTilanne vuoroTilanne;

	protected Pelaaja(RistinollaPalvelin ristinollaPalvelin) throws RemoteException {
		super();
		this.ristinollaPalvelin = ristinollaPalvelin;
		ristinollaPalvelin.rekisteroiPelaaja(this);
		ristinollaPalvelin.liityPeliin(this);
		gui = new GUI();
		pelaakoViela = true;
		vuoroTilanne = VuoroTilanne.VUOROJA_EI_JAETTU;
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub			
		RistinollaPalvelin ristinollaPalvelin = (RistinollaPalvelin) Naming.lookup("rmi://localhost/RistinollaPalvelin");	
		Pelaaja pelaaja = new Pelaaja(ristinollaPalvelin);
		Thread pelaajaSaie = new Thread(pelaaja);
		pelaajaSaie.start();
		
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
		voitot++;
	}
	
	@Override
	public void resetGUI() throws RemoteException {
		gui.ResetGUI();
	}	

	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}
	
	@Override
	public void paivitaPelia() throws RemoteException {
		
	}

	@Override
	public void run() {
		while(pelaakoViela){
			try {
				paivitaPelia();
			} catch (RemoteException e) {
				e.printStackTrace();
				virheet++;
			}
			
			if(virheet == 10){
				pelaakoViela = false;
			}
		}
		System.exit(1);
		
	}

}
