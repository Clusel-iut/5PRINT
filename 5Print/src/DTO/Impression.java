package DTO;
import java.util.Date;
import java.util.ArrayList;

public abstract class Impression{ 
	protected int id_impression;
	protected Date date_impression;
	protected int nb_impression;
	protected Client client;
	protected Catalogue catalogue;
	protected float montant_total;
	protected boolean etat_impression;
	protected ArrayList<Photo> photos;

	public Impression(int id_impression, Date date_impression, int nb_impression, Client client, Catalogue catalogue,
			int numero, float montant_total, boolean etat_impression, ArrayList<Photo> photos) {
		this.id_impression = id_impression;
		this.date_impression = date_impression;
		this.nb_impression = nb_impression;
		this.client = client;
		this.catalogue = catalogue;
		this.montant_total = montant_total;
		this.etat_impression = etat_impression;
		this.photos = photos;
		this.numero = numero;
	}
	
	public int getId_impression() {
		return id_impression;
	}
	public void setId_impression(int id_impression) {
		this.id_impression = id_impression;
	}
	public Date getDate_impression() {
		return date_impression;
	}
	public void setDate_impression(Date date_impression) {
		this.date_impression = date_impression;
	}
	public int getNb_impression() {
		return nb_impression;
	}
	public void setNb_impression(int nb_impression) {
		this.nb_impression = nb_impression;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Catalogue getCatalogue() {
		return catalogue;
	}
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	public float getMontant_total() {
		return montant_total;
	}
	public void setMontant_total(float montant_total) {
		this.montant_total = montant_total;
	}
	public boolean getEtat_impression() {
		return etat_impression;
	}
	public void setEtat_impression(boolean etat_impression) {
		this.etat_impression = etat_impression;
	}

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	protected int numero;

	
}