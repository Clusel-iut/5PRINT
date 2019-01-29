package DTO;

public class PointRelais {
	private String nom;
	private Adresse adresse;

	public PointRelais(String nom, Adresse adresse) {
		super();
		this.nom = nom;
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return "PointRelais [nom=" + nom + ", adresse=" + adresse + "]\n";
	}

}
