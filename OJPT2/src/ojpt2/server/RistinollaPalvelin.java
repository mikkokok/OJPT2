package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ojpt2.Pelaaja;
import ojpt2.Pelaaja.VuoroTilanne;
import ojpt2.PelaajaIF;
import ojpt2.server.TicTacToeLogic.PelinTila;

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
		kaikkiPelit.put(peliID, new TicTacToeLogic(this));
	}
	
	@Override
	public void rekisteroiPelaaja(PelaajaIF pelaaja) throws RemoteException {
		kaikkiPelaajat.put(pelaajaID, pelaaja);
		pelaajaID++;
	}

	@Override
	public void aloitaPeli(TicTacToeLogic peli) throws RemoteException {	
		peli.getPelaaja1().alustaGUI();
		peli.getPelaaja2().alustaGUI();
		
		System.out.println("Tuli aloitaPeli-metodiin");
		
		peli.aloitaPeli();		
	}

	//Metodi joka palauttaa pelin annetun peliID:n mukaan
	public TicTacToeLogic getPeli(int peliID) throws RemoteException {
		
		TicTacToeLogic peli = null;
		
		for (Map.Entry<Integer, TicTacToeLogic> entry : kaikkiPelit.entrySet()) {
			int avain = entry.getKey();
			
			if(avain == peliID){
				peli = entry.getValue();
			}
		}
		return peli;
	}
	
	//Metodi joka luo uuden tyhj‰n pelihuoneen 
	public void luoUusiPeli() throws RemoteException{
		peliID++;
		kaikkiPelit.put(peliID, new TicTacToeLogic(this));
	}
	
	@Override
	//Metodi joka liitt‰‰ pelaajan viimeisimp‰‰n peliin
	public int liityPeliin(PelaajaIF pelaaja) throws RemoteException {
		
		int peliID = kaikkiPelit.size() - 1;
		TicTacToeLogic peli = kaikkiPelit.get(peliID);
				
		//Jos peliss‰ ei ole kahta pelaajaa niin pelaaja voidaan lis‰t‰ peliin
		if(peli.getPelaajienMaara() < 2){ 
			peli.lisaaPelaaja(pelaaja);
			
			if(peli.getPelaajienMaara() == 1){
				peli.pelinTila = PelinTila.ODOTETAAN_TOISTA_PELAAJAA;
			}
			
			//Jos pelaajan lis‰‰misen j‰lkeen peliss‰ on kaksi pelaajaa niin peli voidaan
			//aloittaa ja luodaan samalla uusi tyhj‰ pelihuone
			else if(peli.getPelaajienMaara() == 2){
				luoUusiPeli();
				aloitaPeli(peli);					
			}
			
			return peliID;
		}
		else{
			System.out.println("Peliin ei voi liitty‰ koska se on t‰ynn‰. Luodaan uusi pelihuone");
			luoUusiPeli();
			return 0;
		}
	}
	
	@Override
	//Metodi joka resetoi pelaajien k‰yttˆliittym‰n
	public void resetGUI(TicTacToeLogic peli) throws RemoteException {
		peli.getPelaaja1().resetMyGUI();
		peli.getPelaaja2().resetMyGUI();
		peli.pelinTila = PelinTila.PELIN_ALOITUS;
	}

	@Override
	public void tarkistaVoitto(int peliID) throws RemoteException {
		
		System.out.println("Tuli tarkista voitto - metodiin");
		TicTacToeLogic peli = this.getPeli(peliID);
		
			if(peli.isWin() == "pelaaja1"){
				System.out.println("Pelaaja1 voitti pelin");
				peli.getPelaaja1().voitto();
				peli.getPelaaja2().havio();
				peli.pelinTila = PelinTila.ERAN_LOPPU; 
				peli.run();
				//paivitaPelia(peli);
			}
			else if(peli.isWin() == "pelaaja2"){
				System.out.println("Pelaaja2 voitti pelin");
				peli.getPelaaja2().voitto();
				peli.getPelaaja1().havio();
				peli.pelinTila = PelinTila.ERAN_LOPPU; 
				peli.run();
				//paivitaPelia(peli);
			}
	}
	
	@Override
	//Metodi joka poistaa pelin jos pelaaja1 tai pelaaja2 poistuu pelist‰
	public void poistaPeli(TicTacToeLogic peli) throws RemoteException {
		kaikkiPelit.remove(peli);
	}

	@Override
	public void paivitaPelia(TicTacToeLogic peli) throws RemoteException {
		
		System.out.println("Tuli paivitaPelia metodiin");
		
		while(peli.getPeliKaynnissa()){
			
			//Pelin aloitus tilassa arvotaan kumpi pelaaja saa aloitusvuoron ja 
			//sen j‰lkeen peli voidaan todella k‰ynnist‰‰
			if(peli.getPelinTila() == PelinTila.PELIN_ALOITUS){
				
				//Arvotaan kumpi pelaaja saa aloitusvuoron
				Random random = new Random();		
				int aloitusVuoro = random.nextInt(1);
				
				if(aloitusVuoro == 0) 
					peli.getPelaaja1().otaVuoro();
				else 
					peli.getPelaaja2().otaVuoro();
				
				System.out.println("Vuorot sekoitettu pelaajien kesken");
				peli.pelinTila = PelinTila.PELI_KAYNNISSA;
			}
			
			else if(peli.getPelinTila() == PelinTila.PELI_KAYNNISSA){
				
				if(peli.getPelaaja1().getVuoroTilanne() == VuoroTilanne.MUN_VUORO){
					
					peli.getPelaaja1().vastaanOtaPeliTilanne(peli.getPeliTilanne());
					System.out.println("Pelaaja 1:lle l‰hetetty pelin nykyinen tilanne");
					
					//Ei tehd‰ mit‰‰n kun pelaajan vuoro on kesken
					while(peli.getPelaaja1().onkoVuoroKesken()){						
						try {
							System.out.println("Odotetaan pelaaja 1:sen siirtoa...");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					peli.lisaaSiirto(peli.getPelaaja1().lahetaViimeisinSiirtoni()); 
					
					//Debuggausta varten
					System.out.println("Pelaaja1 p‰ivitti pelitilanteen. Pelitilanne on nyt:");
					System.out.println("------------------");
					
					//Debuggausta varten, katsotaan onko pelitilanne muuttunut
					for(int i = 0; i < peli.getPeliTilanne().length; i++){
						System.out.println(" [" + peli.getPeliTilanne()[i][0] + "] " + " [" + peli.getPeliTilanne()[i][1] + "] " + " [" + peli.getPeliTilanne()[i][2] + "] " );
					}
					
					System.out.println("------------------"); 
					
					peli.getPelaaja1().paataVuoro();
					peli.getPelaaja2().otaVuoro();
					
				}
				else if(peli.getPelaaja2().getVuoroTilanne() == VuoroTilanne.MUN_VUORO){
					
					peli.getPelaaja2().vastaanOtaPeliTilanne(peli.getPeliTilanne());
					System.out.println("Pelaaja 2:lle l‰hetetty pelin nykyinen tilanne");
					
					while(peli.getPelaaja2().onkoVuoroKesken()){
						//Ei tehd‰ mit‰‰n kun pelaajan vuoro on kesken
						try {
							System.out.println("Odotetaan pelaaja 2:sen siirtoa...");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					peli.lisaaSiirto(peli.getPelaaja2().lahetaViimeisinSiirtoni()); 
					
					//Debuggausta varten
					System.out.println("Pelaaja2 p‰ivitti pelitilanteen. Pelitilanne on nyt:");
					System.out.println("------------------");
					
					//Debuggausta varten, katsotaan onko pelitilanne muuttunut
					for(int i = 0; i < peli.getPeliTilanne().length; i++){
						System.out.println(" [" + peli.getPeliTilanne()[i][0] + "] " + " [" + peli.getPeliTilanne()[i][1] + "] " + " [" + peli.getPeliTilanne()[i][2] + "] " );
					}
					
					System.out.println("------------------"); 
					
					peli.getPelaaja2().paataVuoro();
					peli.getPelaaja1().otaVuoro();
				}
				
			}
			
			else if(peli.getPelinTila() == PelinTila.ERAN_LOPPU){
				System.out.println("Pelin er‰ loppui. Resetoidaan peli");
				peli.resetgame();
				peli.getPelaaja1().resetMyGUI();
				peli.getPelaaja2().resetMyGUI();
				peli.pelinTila = PelinTila.PELIN_ALOITUS;
				
			}
			else if(peli.getPelinTila() == PelinTila.PELI_OHI){
				peli.lopetaPeli();
			}
			
		}
		poistaPeli(peli);
		
	}
}
