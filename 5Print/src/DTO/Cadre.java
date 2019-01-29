package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Cadre extends Impression{
	@Override
	public String toString() {
		return "Cadre [mise_en_page=" + mise_en_page + ", modele=" + modele + "]\n";
	}
	private String mise_en_page;
	private String modele;
	
	public Cadre(int id_impression, Date date_impression, int nb_impression, Client client, Stock stock, int numero,
			float montant_total, boolean etat_impression, ArrayList<Photo> photos,Commande commande, String mise_en_page, String modele) {
		super(id_impression, date_impression, nb_impression, client, stock, numero, montant_total, etat_impression, photos, commande);
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