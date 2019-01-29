package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Calendrier extends Impression{
	private String modele;

	public Calendrier(int id_impression, Date date_impression, int nb_impression, Client client, Stock stock, int numero,
			float montant_total, boolean etat_impression, ArrayList<Photo> photos, Commande commande, String modele) {
		super(id_impression, date_impression, nb_impression, client, stock, numero, montant_total, etat_impression, photos, commande);
		this.modele = modele;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	@Override
	public String toString() {
		return "\nCalendrier [modele=" + modele + "]";
	}
	

}