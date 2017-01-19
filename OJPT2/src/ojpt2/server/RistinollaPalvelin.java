package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ojpt2.Pelaaja;

public class RistinollaPalvelin extends UnicastRemoteObject implements RistinollaPalvelinIF{

	private static final long serialVersionUID = 1L;
	private int peliID = 0;
	private int pelaajaID = 0;
	private HashMap<Integer, TicTacToeLogic> kaikkiPelit;
	private HashMap<Integer, Pelaaja> kaikkiPelaajat;
	
	protected RistinollaPalvelin() throws RemoteException {
		super();
		kaikkiPelit = new HashMap<Integer, TicTacToeLogic>();
		kaikkiPelaajat = new HashMap<Integer, Pelaaja>();
		kaikkiPelit.put(peliID, new TicTacToeLogic());
	}
	
	@Override
	public void rekisteroiPelaaja(Pelaaja pelaaja) throws RemoteException {
		kaikkiPelaajat.put(pelaajaID, pelaaja);
	}

	@Override
	public void aloitaPeli(TicTacToeLogic peli) throws RemoteException {
		peli.pelinTila = PelinTila.PELIN_ALOITUS;
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
	
	@Override
	//Metodi joka liitt‰‰ pelaajan viimeisimp‰‰n peliin
	public void liityPeliin(Pelaaja pelaaja) throws RemoteException {
		TicTacToeLogic peli = kaikkiPelit.get(kaikkiPelit.size() - 1);
		
		//Jos peliss‰ ei ole kahta pelaajaa niin pelaaja voidaan lis‰t‰ peliin
		if(peli.getPelaajienMaara() < 2){ 
			peli.lisaaPelaaja(pelaaja);
			
			if(peli.getPelaajienMaara() == 1){
				peli.pelinTila = PelinTila.ODOTETAAN_TOISTA_PELAAJAA;
			}
			
			//Jos pelaajan lis‰‰misen j‰lkeen peliss‰ on kaksi pelaajaa niin peli voidaan
			//aloittaa ja luodaan samalla uusi tyhj‰ pelihuone
			else if(peli.getPelaajienMaara() == 2){
				aloitaPeli(peli);		
				luoUusiPeli();
			}
		}
		else{
			System.out.println("Peliin ei voi liitty‰ koska se on t‰ynn‰. Luodaan uusi pelihuone");
			luoUusiPeli();
		}
	}
	
	//Metodi joka luo uuden tyhj‰n pelihuoneen 
	public void luoUusiPeli() throws RemoteException{
		peliID++;
		kaikkiPelit.put(peliID, new TicTacToeLogic());
	}

	@Override
	//Metodi joka resetoi asiakkaan k‰yttˆliittym‰n
	public void resetGUI(TicTacToeLogic peli) throws RemoteException {
		peli.resetgame();	
	}

	@Override
	//Metodi joka poistaa pelaajan pelist‰
	public void poistaPelaaja(TicTacToeLogic peli, Pelaaja pelaaja) throws RemoteException {
		peli.poistaPelaaja(pelaaja);
	}

}
