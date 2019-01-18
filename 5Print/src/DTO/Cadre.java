package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Cadre extends Impression{
	private String mise_en_page;
	private String modele;
	
	public Cadre(int id_impression, Date date_impression, int nb_impression, Client client, Catalogue catalogue,
			int montant_total, int etat_impression, ArrayList<Photo> photos, String mise_en_page, String modele) {
		super(id_impression, date_impression, nb_impression, client, catalogue, montant_total, etat_impression, photos);
		this.mise_en_page = mise_en_page;
		this.modele = modele;
	}
	
	public String getMise_en_page() {
		return mise_en_page;
	}
	public void setMise_en_page(String mise_en_page) {
		this.mise_en_page = mise_en_page;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
		
}