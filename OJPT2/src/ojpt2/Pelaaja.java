package ojpt2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ojpt2.server.RistinollaPalvelinIF;

/**
 * @author Ville Vahtera ja Mikko Kokkonen
 */
public class Pelaaja extends UnicastRemoteObject implements PelaajaIF, Runnable{

	private static final long serialVersionUID = 1L;
	private GUI gui;
	private RistinollaPalvelinIF ristinollaPalvelin;
	private boolean pelaakoViela; // Muuttuja joka m‰‰rittelee onko pelaaja viel‰ peliss‰ mukana
	private int peliID; // Muuttuja joka kertoon mihin peliin t‰m‰ pelaaja kuuluu
	private String[][] peliTilanne; //Muuttuja, joka ottaa palvelimelta vastaan pelin tilanteen
	private boolean vuoroKesken;
	private boolean voiPaivittaa = false; // Muuttuja joka m‰‰ritt‰‰ voiko pelaajan k‰yttˆliittym‰‰ muokata
	
	//Pelaajan mahdolliset tilanteet vuorojen jaossa
	public enum VuoroTilanne{
		VUOROJA_EI_JAETTU,
		MUN_VUORO,
		VASTUSTAJAN_VUORO
	}
	
	public VuoroTilanne vuoroTilanne; //Muuttuja joka m‰‰rittelee miss‰ tilassa pelaaja on

	/**
	 * @param ristinollaPalvelin
	 * @throws RemoteException
	 */
	protected Pelaaja(RistinollaPalvelinIF ristinollaPalvelin) throws RemoteException {
		super();
		this.ristinollaPalvelin = ristinollaPalvelin;
		gui = new GUI();
		gui.DisableButtons();
		peliTilanne = new String[3][3];
		pelaakoViela = true;
		vuoroKesken = false;
		vuoroTilanne = VuoroTilanne.VUOROJA_EI_JAETTU;
		Thread pelaajaSaie = new Thread(this);
		pelaajaSaie.start();
		ristinollaPalvelin.rekisteroiPelaaja(this);
		peliID = ristinollaPalvelin.liityPeliin(this);
	}
	
	/**
	 *Metodi joka p‰ivitt‰‰ pelaajan GUI:ta niin kauan kuin 
	 *pelaaja on peliss‰ mukana ja jos pelaaja on saanut 
	 *palvelimelta luvan. 
	 */
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
	
	/**
	 * Metodi joka antaa ottaa GUI:n k‰yttˆˆn
	 */
	@Override
	public void alustaGUI() throws RemoteException {
		gui.UpdateTextArea("Peli alustettu");
		gui.UpdateTextArea("----------------------");
		gui.DisableButtons();
	}
	
	/**
	 * Metodi joka antaa pelaajalle vuoron ja ottaa
	 * painikkeet k‰yttˆˆn
	 */
	@Override
	public void otaVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.MUN_VUORO;
		gui.UpdateTextAreab("Mun vuoro");
		gui.EnableButtons();
		vuoroKesken = true;
	}
	
	/**
	 * Metodi joka p‰‰tt‰‰ pelaajan vuoron, jolloin
	 * painikkeet poistetaan k‰ytˆst‰ vastustajan vuoron ajaksi
	 */
	@Override
	public void paataVuoro() throws RemoteException {
		vuoroTilanne = VuoroTilanne.VASTUSTAJAN_VUORO;
		gui.UpdateTextAreab("Vastustajan vuoro");
		gui.DisableButtons();
	}
	
	/**
	 * Metodi joka ilmoittaa pelaajalle ett‰ h‰n voitti pelin
	 */
	@Override
	public void voitto() throws RemoteException {
		gui.UpdateTextArea("Voitit pelin.");
		vuoroKesken = false;
		voiPaivittaa = false;
	}
	
	/**
	 * Metodi joka ilmoittaa pelaajalle ett‰ h‰n h‰visi pelin
	 */
	@Override
	public void havio() throws RemoteException{
		gui.UpdateTextArea("H‰visit pelin.");
		vuoroKesken = false;
		voiPaivittaa = false;
	}
	
	/**
	 * Metodi joka ilmoittaa pelaajalle tasapelist‰
	 */
	@Override
	public void tasapeli() throws RemoteException{
		gui.UpdateTextArea("Tasapeli");
		vuoroKesken = false;
		voiPaivittaa = false;
	}

	/**
	 * Metodi joka ilmoittaa ettei pelaaja ole en‰‰
	 * peliss‰ mukana
	 */
	@Override
	public void poistu() throws RemoteException {
		pelaakoViela = false;		
	}

	/**
	 * Metodi joka palauttaa onko pelaajan vuoro viel‰ kesken
	 */
	public boolean onkoVuoroKesken(){
		return vuoroKesken;
	}

	/**
	 * Metodi joka palauttaa sen pelin ID:n,
	 *  johon t‰m‰ pelaaja kuuluu
	 */
	@Override
	public int getPeliID(){
		return peliID;
	}
	
	/**
	 * Metodi joka palauttaa pelaajan tilanteen omasta vuorostaan
	 */
	@Override
	public VuoroTilanne getVuoroTilanne() throws RemoteException {
		return vuoroTilanne;
	}
	
	/**
	 * Metodi joka p‰ivitt‰‰ pelaajan GUI:ta pelin edetess‰
	 */
	@Override
	public void paivitaGUI() throws RemoteException {
		//gui.UpdateTextAreab(vuoroTilanne.toString());
		
		//Tehd‰‰n vastustajan siirto pelaajan omaan GUI:hin
		gui.teeVastustajanSiirto(peliTilanne); 
		
		//Pyydet‰‰n palvelinta tarkistamaan onko peli voitettu
		ristinollaPalvelin.tarkistaVoitto(peliID, gui.getSiirtojenMaara());
			
		//Silmukka, joka pyˆrii niin kauan kunnes pelaaja on tenyt siirtonsa
		while(vuoroKesken){
			
			//Odotetetaan pelaajan siirtoa			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Kun pelaaja on tehnyt siirtonsa,
			//niin asetetaan oma vuoro p‰‰ttyneeksi
			if(gui.getviimeisinSiirto() != null){
				vuoroKesken = false;
				vuoroTilanne = VuoroTilanne.VASTUSTAJAN_VUORO;
				voiPaivittaa = false;
			}	
		}
	}
	
	/**
	 * Metodi joka vastaanottaa palvelimelta pelin senhetkisen tilanteen ja
	 * resetoidaan samalla pelaajan oma viimeisin siirto. Lopuksi kerrotaan
	 * ett‰ GUI:ta voidaan nyt p‰ivitt‰‰
	 */
	
	@Override
	public void vastaanOtaPeliTilanne(String[][] peliTilanne) throws RemoteException {
		this.peliTilanne = peliTilanne;
		gui.resetviimeisinSiirto();
		voiPaivittaa = true;
		
	}
	
	/**Metodi joka l‰hett‰‰ palvelimelle
	 * pelaajan viimeisimm‰n siirron
	 */
	@Override
	public String[][] lahetaViimeisinSiirtoni() throws RemoteException {
		return gui.getviimeisinSiirto();
	}
}
