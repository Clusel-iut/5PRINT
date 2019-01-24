package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Tirage extends Impression{

	public Tirage(int id_impression, Date date_impression, int nb_impression, Client client, Stock stock, int numero,
			float montant_total, boolean etat_impression, ArrayList<Photo> photos) {
		super(id_impression, date_impression, nb_impression, client, stock, numero, montant_total, etat_impression, photos);
	}

}