package DTO;

import java.util.ArrayList;

public class Client {
	
	private String email;
	private String nom;
	private String prenom;
	private String motDePasse;
	private ArrayList<Adresse> adresses = new ArrayList<Adresse>();
	private ArrayList<FichierPhoto> photos = new ArrayList<FichierPhoto>(); //Les photos dont il est propri�taire
	private ArrayList<FichierPhoto> photos_partagees = new ArrayList<FichierPhoto>(); //Les photos qui ne sont pas � lui
	private ArrayList<Impression> impressions = new ArrayList<Impression>();
	private ArrayList<Commande> commandes = new ArrayList<Commande>();

	public Client(String email, String nom, String prenom, ArrayList<Adresse> adresses, String motDePasse,
			ArrayList<FichierPhoto> photosP, ArrayList<FichierPhoto> photos, ArrayList<Impression> impressions, ArrayList<Commande> commandes) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.adresses = adresses;
		this.motDePasse = motDePasse;
		this.adresses = adresses;
		this.photos = photos;
		this.photos_partagees = photosP;
		this.setImpressions(impressions);
		this.setCommandes(commandes);
	}
	
	public Client(String email, String nom, String prenom, String motDePasse) {
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motDePasse;
		this.adresses = new ArrayList<Adresse>();
		this.photos = new ArrayList<FichierPhoto>();
		this.photos_partagees = new ArrayList<FichierPhoto>();
		this.setImpressions(new ArrayList<Impression>());
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
		this.setImpressions(new ArrayList<Impression>());
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
	
	public void addPhotoPartagee(FichierPhoto fichier) {
		this.photos_partagees.add(fichier);
	}
	
	public void addPhoto(FichierPhoto fichier) {
		this.photos.add(fichier);
	}

	public ArrayList<Impression> getImpressions() {
		return impressions;
	}

	public void setImpressions(ArrayList<Impression> impressions) {
		this.impressions = impressions;
	}
	
	public void addImpression(Impression impression) {
		this.impressions.add(impression);
	}

	public ArrayList<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(ArrayList<Commande> commandes) {
		this.commandes = commandes;
	}
	
	public void addCommande(Commande commande) {
		this.commandes.add(commande);
	}

	@Override
	public String toString() {
		return "\nClient [email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", motDePasse=" + motDePasse
				+ ", adresses=" + adresses + ", photos=" + photos + ", photos_partagees=" + photos_partagees
				+ ", impressions=" + impressions + ", commandes=" + commandes + "]";
	}	
}
