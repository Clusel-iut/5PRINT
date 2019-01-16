package main;

import java.util.ArrayList;
import java.util.Date;

public class Agenda extends Impression{
	private String modele;
	private int nbPages;

	
	public Agenda(int id_impression, Date date_impression, int nb_impression, Client client, Catalogue catalogue,
			int montant_total, int etat_impression, ArrayList<Photo> photos) {
		super(id_impression, date_impression, nb_impression, client, catalogue, montant_total, etat_impression, photos);
		// TODO Auto-generated constructor stub
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