package ojpt2.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RistinollaPalvelin extends UnicastRemoteObject implements RistinollaPalvelinIF{

	private static final long serialVersionUID = 1L;
	
	private Pelaaja pelaaja1;
	private Pelaaja pelaaja2;

	protected RistinollaPalvelin() throws RemoteException {
		super();
		pelaaja1 = null;
		pelaaja2 = null;
		
	}

	@Override
	public void lisaaPelaaja(Pelaaja pelaaja) throws RemoteException {
		if(pelaaja1 == null){
			pelaaja1 = pelaaja;
		}
		else if(pelaaja2 == null){
			pelaaja2 = pelaaja;
		}
		
	}

	@Override
	public void aloitaPeli() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lopetaPeli() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVuoro() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTila() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPeli() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maaritaVoittaja() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
