package DTO;

import java.util.ArrayList;
import java.util.Date;

public class FichierPhoto{
	private String chemin;
	private Client client;
	private String resolution;
	private String info_prise_de_vue;
	private Date date_ajout;
	private Date date_no_photo;
	private boolean est_partage;
	private ArrayList<Client> clients_partages;
	private ArrayList<Photo> photos;
	
	public FichierPhoto(String chemin, Client client, String resolution, Date date_ajout, Date date_no_photo, String info_prise_de_vue, boolean est_partage,
			ArrayList<Client> clients_partages, ArrayList<Photo> photos) {
		this.chemin = chemin;
		this.client = client;
		this.resolution = resolution;
		this.setDate_ajout(date_ajout);
		this.setDate_no_photo(date_no_photo);
		this.info_prise_de_vue = info_prise_de_vue;
		this.est_partage = est_partage;
		this.clients_partages = clients_partages;
		this.photos = photos;
	}
	public String getChemin() {
		return chemin;
	}
	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getInfo_prise_de_vue() {
		return info_prise_de_vue;
	}
	public void setInfo_prise_de_vue(String info_prise_de_vue) {
		this.info_prise_de_vue = info_prise_de_vue;
	}
	public boolean getEst_partage() {
		return est_partage;
	}
	public void setEst_partage(boolean est_partage) {
		this.est_partage = est_partage;
	}
	public ArrayList<Client> getClients_partages() {
		return clients_partages;
	}
	public void setClients_partages(ArrayList<Client> clients_partages) {
		this.clients_partages = clients_partages;
	}
	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	public void addClient(Client client) {
		this.clients_partages.add(client);
	}
	public void addPhoto(Photo photo) {
		this.photos.add(photo); 
	}
	public Date getDate_no_photo() {
		return date_no_photo;
	}
	public void setDate_no_photo(Date date_no_photo) {
		this.date_no_photo = date_no_photo;
	}
	public Date getDate_ajout() {
		return date_ajout;
	}
	public void setDate_ajout(Date date_ajout) {
		this.date_ajout = date_ajout;
	}
	@Override
	public String toString() {
		return  this.est_partage ? "Partagée; " : "Perso; " + "chemin=" + chemin + " resolution=" + resolution + " date_ajout=" + date_ajout;
	}
	
	
}