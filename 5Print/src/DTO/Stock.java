package DTO;

public class Stock {
	private TypeSupport type_support;
	private String qualite;
	private String format;
	private int quantite;
	private int prix;
	public Stock(TypeSupport type_support, String qualite, String format, int quantite, int prix) {
		super();
		this.type_support = type_support;
		this.qualite = qualite;
		this.format = format;
		this.quantite = quantite;
		this.prix = prix;
	}
	public TypeSupport getType_support() {
		return type_support;
	}
	public void setType_support(TypeSupport type_support) {
		this.type_support = type_support;
	}
	public String getQualite() {
		return qualite;
	}
	public void setQualite(String qualite) {
		this.qualite = qualite;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	@Override
	public String toString() {
		return "\nStock [type_support=" + type_support.toString() + ", qualite=" + qualite + ", format=" + format + ", prix=" + prix
				+ "€]";
	}
	
	
}
