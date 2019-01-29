package DTO;

public class BonAchat {
	@Override
	public String toString() {
		return "BonAchat [code_bon=" + code_bon + ", commande=" + commande + ", commandeGeneree=" + commandeGeneree
				+ ", client=" + client + ", pourcentage_reduc=" + pourcentage_reduc + ", type_bon_achat="
				+ type_bon_achat + "]\n";
	}

	private String code_bon;
	private Commande commande;
	private Commande commandeGeneree;
	private Client client;
	private int pourcentage_reduc;
	private String type_bon_achat;
	
	public BonAchat(String code_bon, Commande commande, Commande commandeG, Client client, int pourcentage_reduc, String type_bon_achat) {
		this.code_bon = code_bon;
		this.commande = commande;
		this.commandeGeneree = commandeG;
		this.client = client;
		this.pourcentage_reduc = pourcentage_reduc;
		this.type_bon_achat = type_bon_achat;
	}

	public String getCode_bon() {
		return code_bon;
	}

	public void setCode_bon(String code_bon) {
		this.code_bon = code_bon;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	public Commande getCommandeGeneree() {
		return commandeGeneree;
	}

	public void setCommandeGeneree(Commande commandeGeneree) {
		this.commandeGeneree = commandeGeneree;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getPourcentage_reduc() {
		return pourcentage_reduc;
	}

	public void setPourcentage_reduc(int pourcentage_reduc) {
		this.pourcentage_reduc = pourcentage_reduc;
	}

	public String getType_bon_achat() {
		return type_bon_achat;
	}

	public void setType_bon_achat(String type_bon_achat) {
		this.type_bon_achat = type_bon_achat;
	}
	
	
}