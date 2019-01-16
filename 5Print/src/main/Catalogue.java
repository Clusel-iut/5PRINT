package main;

public class Catalogue {
	private String format;
	private String qualite;
	private int prix;
	public Catalogue(String format, String qualite, int prix) {
		super();
		this.format = format;
		this.qualite = qualite;
		this.prix = prix;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getQualite() {
		return qualite;
	}
	public void setQualite(String qualite) {
		this.qualite = qualite;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
}
