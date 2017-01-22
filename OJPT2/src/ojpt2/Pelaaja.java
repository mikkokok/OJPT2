package ojpt2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ojpt2.server.RistinollaPalvelinIF;

public class Pelaaja extends UnicastRemoteObject implements PelaajaIF, Runnable{

	private static final long serialVersionUID = 1L;
	
	private GUI gui;
	private RistinollaPalvelinIF ristinollaPalvelin;
	private boolean pelaakoViela;
	private int peliID;
	private int voitot = 0;
	private int virheet = 0;
	private String[][] peliTilanne;
	private boolean vuoroKesken;
	private boolean voiPaivittaa = false;
	
	public enum VuoroTilanne{
		VUOROJA_EI_JAETTU,
		MUN_VUORO,
		VASTUSTAJAN_VUORO
	}
	
	public VuoroTilanne vuoroTilanne;

	protected Pelaaja(RistinollaPalvelinIF ristinollaPalvelin) throws RemoteException {
		super();
		this.ristinollaPalvelin = ristinollaPalvelin;
		gui = new GUI();
		gui.DisableButtons();
		pelaakoViela = true;
		vuoroKesken = false;
		vuoroTilanne = VuoroTilanne.VUOROJA_EI_JAETTU;
		ristinollaPalvelin.rekisteroiPelaaja(this);
		peliID = ristinollaPalvelin.liityPeliin(this);
	}
	
	@Override
	public void alustaGUI() throws RemoteException {
		gui.UpdateTextArea("Peli alustettu");
		gui.UpdateTextArea("----------------------");
	}
	
	@Override
	public void otaVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.MUN_VUORO;
		gui.EnableButtons();
		vuoroKesken = true;
	}
	
	@Override
	public void paataVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.VASTUSTAJAN_VUORO;
		gui.UpdateTextAreab("Vastustajan vuoro");
		gui.DisableButtons();
	}
	
	@Override
	public void voitto() throws RemoteException {
		voitot++;
	}
	
	@Override
	public void resetMyGUI() throws RemoteException {
		gui.ResetGUI();
	}	

	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}

	public boolean onkoVuoroKesken(){
		return vuoroKesken;
	}

	//Metodi joka palauttaa sen pelin ID: johon t‰m‰ pelaaja kuuluu
	@Override
	public int getPeliID(){
		return peliID;
	}
	
	@Override
	public VuoroTilanne getVuoroTilanne() throws RemoteException {
		return vuoroTilanne;
	}
	
	@Override
	public void paivitaGUI() throws RemoteException {
		//Tehd‰‰n vastustajan siirto pelaajan omaan GUI:hin
		gui.teeVastustajanSiirto(peliTilanne); 
		gui.UpdateTextAreab("Mun vuoro");
			
		while(vuoroKesken){
			//Odotetetaan pelaajan omia muutoksia GUI:hin
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(gui.getviimeisinSiirto() != null){
				for(int i = 0; i < gui.getviimeisinSiirto().length; i++){
					for(int j = 0; j < gui.getviimeisinSiirto()[i].length; j++){
						if(peliTilanne[i][j] != null){
							peliTilanne[i][j] = gui.getviimeisinSiirto()[i][j];
						}
					}
				}
				gui.resetviimeisinSiirto();
				voiPaivittaa = false;
				vuoroKesken = false;
			}	
		}
	}
	
	//Metodi joka vastaanottaa palvelimelta pelin senhetkisen tilanteen eli
	//vastustajan viimeisimm‰n siirron
	@Override
	public void vastaanOtaPeliTilanne(String[][] peliTilanne) throws RemoteException {
		this.peliTilanne = peliTilanne;
		voiPaivittaa = true;
		
	}
	
	//Metodi joka l‰hett‰‰ palvelimelle pelin senhetkisen tilanteen eli
	//pelaajan oman viimeisimm‰n siirron
	@Override
	public String[][] lahetaPelinTilanne() throws RemoteException {
		return peliTilanne;
	}
	
	//Metodi joka p‰ivitt‰‰ pelaajan GUI:ta niin kauan kuin 
	//pelaaja on peliss‰ mukana.
	@Override
	public void run() {
		while(pelaakoViela){	
			
			if(voiPaivittaa){
				try {
					paivitaGUI();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		System.exit(1);	
	}

}
