package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Album extends Impression{
	
	private String titre;
	private String mise_en_page;
	
	public Album(int id_impression, Date date_impression, int nb_impression, Client client, Stock stock, int numero,
			float montant_total, boolean etat_impression, ArrayList<Photo> photos, String titre, String mise_en_page) {
		super(id_impression, date_impression, nb_impression, client, stock, numero, montant_total, etat_impression, photos);
		this.titre = titre;
		this.mise_en_page = mise_en_page;
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getMise_en_page() {
		return mise_en_page;
	}

	public void setMise_en_page(String mise_en_page) {
		this.mise_en_page = mise_en_page;
	}
}