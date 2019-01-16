package main;

public class Photo{
	private int id_photo;
	private Impression impression;
	private String description;
	private String retouche;
	private int numero_page;
	private int position_X;
	private int position_Y;
	private int nb_exemplaire; // Pour un tirage
	
	public Photo(int id_photo, Impression impression, String description, String retouche, int numero_page,
			int position_X, int position_Y, int nb_exemplaire) {
		super();
		this.id_photo = id_photo;
		this.impression = impression;
		this.description = description;
		this.retouche = retouche;
		this.numero_page = numero_page;
		this.position_X = position_X;
		this.position_Y = position_Y;
		this.nb_exemplaire = nb_exemplaire;
	}

	public int getId_photo() {
		return id_photo;
	}

	public void setId_photo(int id_photo) {
		this.id_photo = id_photo;
	}

	public Impression getImpression() {
		return impression;
	}

	public void setImpression(Impression impression) {
		this.impression = impression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRetouche() {
		return retouche;
	}

	public void setRetouche(String retouche) {
		this.retouche = retouche;
	}

	public int getNumero_page() {
		return numero_page;
	}

	public void setNumero_page(int numero_page) {
		this.numero_page = numero_page;
	}

	public int getPosition_X() {
		return position_X;
	}

	public void setPosition_X(int position_X) {
		this.position_X = position_X;
	}

	public int getPosition_Y() {
		return position_Y;
	}

	public void setPosition_Y(int position_Y) {
		this.position_Y = position_Y;
	}

	public int getNb_exemplaire() {
		return nb_exemplaire;
	}

	public void setNb_exemplaire(int nb_exemplaire) {
		this.nb_exemplaire = nb_exemplaire;
	}
	
	
}