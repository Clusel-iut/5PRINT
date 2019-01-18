package DTO;

public class Adresse {
	private int id_adresse;
	private String pays;
	private String ville;
	private String code_postal;
	private String rue;
	private int numero;	
	
	public Adresse(int id_adresse, String pays,  String ville, String code_postal, String rue, int numero) {
		this.id_adresse = id_adresse;
		this.pays = pays;
		this.ville = ville;
		this.code_postal = code_postal;
		this.rue = rue;
		this.numero = numero;
	}
	public int getId_adresse() {
		return id_adresse;
	}
	public void setId_adresse(int id_adresse) {
		this.id_adresse = id_adresse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getPays() {
		return numero;
	}
	public void setPays(int numero) {
		this.numero = numero;
	}
	
}
