package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Agenda extends Impression{
	private String modele;
	private int nbPages;

	public Agenda(int id_impression, Date date_impression, int nb_impression, Client client, Stock stock, int numero,
			float montant_total, boolean etat_impression, ArrayList<Photo> photos,Commande commande, String modele) {
		super(id_impression, date_impression, nb_impression, client, stock, numero, montant_total, etat_impression, photos, commande);
		this.modele = modele;
		if(modele == "365")
			this.nbPages = 365;
		else 
			this.nbPages = 52;
	}

	public String getModele() {
		return modele;
	}


	public void setModele(String modele) {
		this.modele = modele;
	}


	public int getNbPages() {
		return nbPages;
	}


	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}
	
}