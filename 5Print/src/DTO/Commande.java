package DTO;

import java.util.Date;

public class Commande{
	private int numero;
	private BonAchat bon_achat;
	private Adresse adresse;
	private Client client;
	private String mode_livraison;
	private Date date_commande;
	private String statut;
	private int etat_paiement;
	private int montant_total_cmd;
		
	public Commande(int numero, BonAchat bon_achat, Adresse adresse, Client client, String mode_livraison,
			Date date_commande, String statut, int etat_paiement, int montant_total_cmd) {
		super();
		this.numero = numero;
		this.bon_achat = bon_achat;
		this.adresse = adresse;
		this.client = client;
		this.mode_livraison = mode_livraison;
		this.date_commande = date_commande;
		this.statut = statut;
		this.etat_paiement = etat_paiement;
		this.montant_total_cmd = montant_total_cmd;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public BonAchat getBon_achat() {
		return bon_achat;
	}

	public void setBon_achat(BonAchat bon_achat) {
		this.bon_achat = bon_achat;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getMode_livraison() {
		return mode_livraison;
	}

	public void setMode_livraison(String mode_livraison) {
		this.mode_livraison = mode_livraison;
	}

	public Date getDate_commande() {
		return date_commande;
	}

	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getEtat_paiement() {
		return etat_paiement;
	}

	public void setEtat_paiement(int etat_paiement) {
		this.etat_paiement = etat_paiement;
	}

	public int getMontant_total_cmd() {
		return montant_total_cmd;
	}

	public void setMontant_total_cmd(int montant_total_cmd) {
		this.montant_total_cmd = montant_total_cmd;
	}

	
}