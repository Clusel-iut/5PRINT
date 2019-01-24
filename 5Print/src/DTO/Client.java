package DTO;

import java.util.ArrayList;

public class Client {
	
	private String email;
	private String nom;
	private String prenom;
	private String motDePasse;
	private ArrayList<Adresse> adresses;
	private ArrayList<FichierPhoto> photos; //Les photos dont il est propri�taire
	private ArrayList<FichierPhoto> photos_partagees; //Les photos qui ne sont pas � lui

	public Client(String email, String nom, String prenom, ArrayList<Adresse> adresses, String motDePasse,
			ArrayList<FichierPhoto> photosP, ArrayList<FichierPhoto> photos) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.adresses = adresses;
		this.motDePasse = motDePasse;
		this.adresses = adresses;
		this.photos = photos;
		this.photos_partagees = photosP;
	}
	
	public Client(String email, String nom, String prenom, String motDePasse) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motDePasse;
		this.adresses = new ArrayList<Adresse>();
		this.photos = new ArrayList<FichierPhoto>();
		this.photos_partagees = new ArrayList<FichierPhoto>();
	}
	
	public Client(String email, String nom, String prenom, String motDePasse, Adresse adresse) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motDePasse;
		this.adresses = new ArrayList<Adresse>();
		this.adresses.add(adresse);
		this.photos = new ArrayList<FichierPhoto>();
		this.photos_partagees = new ArrayList<FichierPhoto>();
	}

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public ArrayList<Adresse> getAdresse() {
		return adresses;
	}

	public void setAdresse(ArrayList<Adresse> adresse) {
		this.adresses = adresse;
	}
	
	public void addAdresse(Adresse adresse){
		this.adresses.add(adresse);
	}
	
	public void removeAdresse(Adresse adresse){
		this.adresses.remove(adresse);
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public boolean inscrire(String email, String nom, String prenom, String adresse, String motDePasse) {
		// TODO Insertion dans la base
		
		return true;
	}
	
	public boolean telechargerImage(String chemin){
		// TODO Insertion dans la base de l'image
		return true;
	}

	public ArrayList<FichierPhoto> getPhotos_partagees() {
		return photos_partagees;
	}

	public void setPhotos_partagees(ArrayList<FichierPhoto> photos_partagees) {
		this.photos_partagees = photos_partagees;
	}

	public ArrayList<FichierPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<FichierPhoto> photos) {
		this.photos = photos;
	}
	
	
	
	
}
