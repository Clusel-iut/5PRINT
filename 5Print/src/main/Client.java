package main;

import java.util.ArrayList;

public class Client {
	
	private String email;
	private String nom;
	private String prenom;
	private Adresse adresse;
	private String motDePasse;
	private ArrayList<FichierPhoto> photos; //Les photos dont il est propri�taire
	private ArrayList<FichierPhoto> photos_partagees; //Les photos qui ne sont pas � lui
	
	public Client(String email, String nom, String prenom, Adresse adresse) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}
	
	public Client(String email, String nom, String prenom, Adresse adresse, String motDePasse,
			ArrayList<FichierPhoto> photosP, ArrayList<FichierPhoto> photos) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.setPhotos_partagees(photosP);
		this.setPhotos(photos);
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

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
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
