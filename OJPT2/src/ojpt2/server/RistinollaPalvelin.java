package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import ojpt2.Pelaaja.VuoroTilanne;
import ojpt2.PelaajaIF;
import ojpt2.server.TicTacToeLogic.PelinTila;

/**
 * 
 * @author Ville Vahtera ja Mikko Kokkonen
 *
 */
public class RistinollaPalvelin extends UnicastRemoteObject implements RistinollaPalvelinIF{

	private static final long serialVersionUID = 1L;
	private int peliID = 0;
	private int pelaajaID = 0;
	private HashMap<Integer, TicTacToeLogic> kaikkiPelit;
	private HashMap<Integer, PelaajaIF> kaikkiPelaajat;
	
	
	protected RistinollaPalvelin() throws RemoteException {
		super();
		kaikkiPelit = new HashMap<Integer, TicTacToeLogic>();
		kaikkiPelaajat = new HashMap<Integer, PelaajaIF>();
		kaikkiPelit.put(peliID, new TicTacToeLogic(this)); //Luodaan 1 peli heti kun palvelin k‰ynnistyy
	}//Konstruktori
	
	/**
	 * Metodi joka lis‰‰ uuden pelaajan palvelimelle
	 */
	@Override
	public void rekisteroiPelaaja(PelaajaIF pelaaja) throws RemoteException {
		kaikkiPelaajat.put(pelaajaID, pelaaja);
		System.out.println("Pelaajan rekisterˆinti onnistui. Pelaajan ID on: " + pelaajaID);
		pelaajaID++;
	}

	/**
	 * Metodi joka antaa pelille luvan k‰ynnist‰‰ itsens‰
	 */
	@Override
	public void aloitaPeli(TicTacToeLogic peli) throws RemoteException {	
		peli.getPelaaja1().alustaGUI();
		peli.getPelaaja2().alustaGUI();
		peli.aloitaPeli();		
	}

	/**
	 * Metodi joka palauttaa pelin annetun peliID:n mukaan
	 */
	public TicTacToeLogic getPeli(int peliID) throws RemoteException {		
		return kaikkiPelit.get(peliID);	
	}
	
	private int getPelinID(TicTacToeLogic peli){
	     for (int peliID : kaikkiPelit.keySet()) {
             if (kaikkiPelit.get(peliID).equals(peli)) {
               return peliID;
             }
           }
		return 0;
	}
	
	/**
	 * Metodi joka luo uuden tyhj‰n pelihuoneen 
	 */
	public void luoUusiPeli() throws RemoteException{
		peliID++;
		kaikkiPelit.put(peliID, new TicTacToeLogic(this));
	}
	
	/**
	 *Metodi joka liitt‰‰ pelaajan viimeisimp‰‰n peliin
	 */
	@Override
	public void liityPeliin(PelaajaIF pelaaja) throws RemoteException {
		
		//uusinPeliID = kaikkiPelit.size() - 1;
		TicTacToeLogic peli = kaikkiPelit.get(this.peliID);
				
		//Jos peliss‰ ei ole kahta pelaajaa niin pelaaja voidaan lis‰t‰ peliin
		if(peli.getPelaajienMaara() < 2){ 
			peli.lisaaPelaaja(pelaaja);
			
			System.out.println("Pelaaja kuuluu peliin: " + peliID);
		
			//Jos pelaajan lis‰‰misen j‰lkeen peliss‰ on kaksi pelaajaa niin peli voidaan
			//aloittaa ja luodaan samalla uusi tyhj‰ pelihuone
			if(peli.getPelaajienMaara() == 2){
				luoUusiPeli();
				aloitaPeli(peli);					
			}
		}
		else{
			System.out.println("Peliin ei voi liitty‰ koska se on t‰ynn‰. Luodaan uusi pelihuone ja yrit‰ uudelleen");
			luoUusiPeli();
		}
	}

	/**
	 * Metodi joka tarkistaa mik‰li peliss‰ on voittaja
	 */
	@Override
	public void tarkistaVoitto(int peliID, int maxSiirrot) throws RemoteException {
		
		System.out.println("Tarkistetaan pelin: " + peliID + " tilanne.");
		TicTacToeLogic peli = getPeli(peliID);		
		
		//Mik‰li voittaja lˆytyy niin l‰hetet‰‰n pelaajille tieto siit‰
		//kumpi voitti ja asetetaan pelin tila p‰‰ttyneeksi
		if(peli.aloittikoPelaaja1()){
			if(peli.isWin().equals("O")){
				System.out.println("Pelaaja1 voitti pelin");
				peli.getPelaaja1().voitto();
				peli.getPelaaja2().havio();
				peli.pelinTila = PelinTila.PELI_OHI; 
			}
			else if(peli.isWin().equals("X")){
				System.out.println("Pelaaja2 voitti pelin");
				peli.getPelaaja2().voitto();
				peli.getPelaaja1().havio();
				peli.pelinTila = PelinTila.PELI_OHI; 
			}
			else if(peli.isWin().equals("none") && maxSiirrot == 9){
				System.out.println("Tasapeli");
				peli.getPelaaja1().tasapeli();
				peli.getPelaaja2().tasapeli();
				peli.pelinTila = PelinTila.PELI_OHI;
			}
		}
		else{
			if(peli.isWin().equals("O")){
				System.out.println("Pelaaja2 voitti pelin");
				peli.getPelaaja2().voitto();
				peli.getPelaaja1().havio();
				peli.pelinTila = PelinTila.PELI_OHI; 
			}
			else if(peli.isWin().equals("X")){
				System.out.println("Pelaaja1 voitti pelin");
				peli.getPelaaja1().voitto();
				peli.getPelaaja2().havio();
				peli.pelinTila = PelinTila.PELI_OHI; 
			}
			else if(peli.isWin().equals("none") && maxSiirrot == 9){
				System.out.println("Tasapeli");
				peli.getPelaaja1().tasapeli();
				peli.getPelaaja2().tasapeli();
				peli.pelinTila = PelinTila.PELI_OHI;
			}	
		}

	}
	
	
	public void keskeytaPeli(int peliID) throws RemoteException{
		TicTacToeLogic peli = kaikkiPelit.get(peliID);
		peli.lopetaPeli();
		kaikkiPelit.put(peliID++, new TicTacToeLogic(this));
	}
	
	/**
	 * Metodi joka poistaa pelin palvelimen muistista ja 
	 * lopettaa pelaajien toiminnan
	 */
	@Override
	public void poistaPeli(TicTacToeLogic peli) throws RemoteException {
		kaikkiPelaajat.values().remove(peli.getPelaaja1());
		kaikkiPelaajat.values().remove(peli.getPelaaja2());
		peli.getPelaaja1().poistu();
		peli.getPelaaja2().poistu();	
		kaikkiPelit.values().remove(peli);
	}

	/**
	 * Metodi joka p‰ivitt‰‰ peli‰ jakamalla vuorot pelaajien kesken ja l‰hett‰m‰ll‰
	 * tiedon pelin tilanteesta pelaajille
	 */
	@Override
	public void paivitaPelia(TicTacToeLogic peli) throws RemoteException {
		
		//Pelin aloitus tilassa arvotaan kumpi pelaaja saa aloitusvuoron ja 
		//sen j‰lkeen peli voidaan todella k‰ynnist‰‰
		if(peli.getPelinTila() == PelinTila.PELIN_ALOITUS){
				
			peli.getPelaaja1().setPeliID(getPelinID(peli));
			peli.getPelaaja2().setPeliID(getPelinID(peli));
			
			Random random = new Random();		
			int aloitusVuoro = random.nextInt(2);
				
			if(aloitusVuoro == 0){ 
				peli.setKumpiAloitti(true);
				peli.getPelaaja1().otaVuoro();
			}
			else{
				peli.setKumpiAloitti(false);
				peli.getPelaaja2().otaVuoro();
			}
				
			System.out.println("Vuorot sekoitettu pelaajien kesken");
			peli.pelinTila = PelinTila.PELI_KAYNNISSA;
		}
			
		//Pelin k‰ynniss‰ - tilassa kysyt‰‰n kummalla pelaajista on vuoro
		//ja sen mukaan v‰litet‰‰n tietoa pelin tilasta pelaajalle
		else if(peli.getPelinTila() == PelinTila.PELI_KAYNNISSA){
				
			if(peli.getPelaaja1().getVuoroTilanne() == VuoroTilanne.MUN_VUORO){
					
				//Pyydet‰‰n pelaajaa vastaanottamaan pelin nykyinen tilanne
				peli.getPelaaja1().vastaanOtaPeliTilanne(peli.getPeliTilanne());
				System.out.println("Pelaaja 1:lle l‰hetetty pelin nykyinen tilanne");
					
				//Ei tehd‰ mit‰‰n kun pelaajan vuoro on kesken
				while(peli.getPelaaja1().onkoVuoroKesken()){						
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
				//Kun pelaajan vuoro on p‰‰ttynyt niin lis‰t‰‰n pelaajan 
				//viimeisin siirto pelin tilanteeseen
				peli.lisaaSiirto(peli.getPelaaja1().lahetaViimeisinSiirtoni()); 
					
				//Debuggausta varten
				System.out.println("Pelaaja1 p‰ivitti pelitilanteen. Pelitilanne on nyt:");
				System.out.println("------------------");
					
				//Tulostetaam pelitilanne
				for(int i = 0; i < peli.getPeliTilanne().length; i++){
					System.out.println(" [" + peli.getPeliTilanne()[i][0] + "] " + " [" + peli.getPeliTilanne()[i][1] + "] " + " [" + peli.getPeliTilanne()[i][2] + "] " );
				}
					
				System.out.println("------------------"); 
					
				//Jaetaan vuorot
				peli.getPelaaja1().paataVuoro();
				peli.getPelaaja2().otaVuoro();
					
			}
			else if(peli.getPelaaja2().getVuoroTilanne() == VuoroTilanne.MUN_VUORO){
				
				//Pyydet‰‰n pelaajaa vastaanottamaan pelin nykyinen tilanne
				peli.getPelaaja2().vastaanOtaPeliTilanne(peli.getPeliTilanne());
				System.out.println("Pelaaja 2:lle l‰hetetty pelin nykyinen tilanne");
					
				//Ei tehd‰ mit‰‰n kun pelaajan vuoro on kesken
				while(peli.getPelaaja2().onkoVuoroKesken()){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
				//Kun pelaajan vuoro on p‰‰ttynyt niin lis‰t‰‰n pelaajan 
				//viimeisin siirto pelin tilanteeseen
				peli.lisaaSiirto(peli.getPelaaja2().lahetaViimeisinSiirtoni()); 
					
				System.out.println("Pelaaja2 p‰ivitti pelitilanteen. Pelitilanne on nyt:");
				System.out.println("------------------");
					
				//Tulostetaan pelitilanne
				for(int i = 0; i < peli.getPeliTilanne().length; i++){
					System.out.println(" [" + peli.getPeliTilanne()[i][0] + "] " + " [" + peli.getPeliTilanne()[i][1] + "] " + " [" + peli.getPeliTilanne()[i][2] + "] " );
				}
					
				System.out.println("------------------"); 
				
				//Jaetaan vuorot
				peli.getPelaaja2().paataVuoro();
				peli.getPelaaja1().otaVuoro();
			}
				
		}	
		
		//Peli ohi - tilassa pyydet‰‰n peli‰ sulkemaan itsens‰
		else if(peli.getPelinTila() == PelinTila.PELI_OHI){
			peli.lopetaPeli();
		}
	}
}
