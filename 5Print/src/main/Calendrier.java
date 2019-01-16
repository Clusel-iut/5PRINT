package main;

import java.util.ArrayList;
import java.util.Date;

public class Calendrier extends Impression{
	private String modele;

	public Calendrier(int id_impression, Date date_impression, int nb_impression, Client client, Catalogue catalogue,
			int montant_total, int etat_impression, ArrayList<Photo> photos, String modele) {
		super(id_impression, date_impression, nb_impression, client, catalogue, montant_total, etat_impression, photos);
		this.modele = modele;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}
	

}