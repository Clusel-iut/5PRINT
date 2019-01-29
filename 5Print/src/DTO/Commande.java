package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Commande{
	private int numero;
	private BonAchat bon_achat;
	private BonAchat bon_achat_genere;
	private Adresse adresse;
	private Client client;
	private ArrayList<Impression> impressions;
	private String mode_livraison;
	private Date date_commande;
	private StatutCommande statut;
	private boolean etat_paiement;
	private float montant_total_cmd;
	
	public Commande(int numero, BonAchat bon_achat, BonAchat bon_achat_g, Adresse adresse, Client client, ArrayList<Impression> impressions,String mode_livraison,
			Date date_commande, StatutCommande statut, boolean etat_paiement, float montant_total_cmd) {
		super();
		this.numero = numero;
		this.bon_achat = bon_achat;
		this.bon_achat_genere = bon_achat_g;
		this.adresse = adresse;
		this.client = client;
		this.setImpressions(impressions);
		this.mode_livraison = mode_livraison;
		this.date_commande = date_commande;
		this.statut = statut;
		this.etat_paiement = etat_paiement;
		this.montant_total_cmd = montant_total_cmd;
	}
	
	public Commande(int numero, BonAchat bon_achat, BonAchat bon_achat_g, Adresse adresse, Client client,String mode_livraison,
			Date date_commande, StatutCommande statut, boolean etat_paiement, float montant_total_cmd) {
		super();
		this.numero = numero;
		this.bon_achat = bon_achat;
		this.bon_achat_genere = bon_achat_g;
		this.adresse = adresse;
		this.client = client;
		this.setImpressions(new ArrayList<Impression>());
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
	
	public BonAchat getBon_achat_genere() {
		return bon_achat_genere;
	}

	public void setBon_achat_genere(BonAchat bon_achat_genere) {
		this.bon_achat_genere = bon_achat_genere;
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

	public StatutCommande getStatut() {
		return statut;
	}

	public void setStatut(StatutCommande statut) {
		this.statut = statut;
	}

	public boolean getEtat_paiement() {
		return etat_paiement;
	}

	public void setEtat_paiement(boolean etat_paiement) {
		this.etat_paiement = etat_paiement;
	}

	public float getMontant_total_cmd() {
		return montant_total_cmd;
	}

	public void setMontant_total_cmd(float montant_total_cmd) {
		this.montant_total_cmd = montant_total_cmd;
	}

	public ArrayList<Impression> getImpressions() {
		return impressions;
	}

	public void setImpressions(ArrayList<Impression> impressions) {
		this.impressions = impressions;
	}

	@Override
	public String toString() {
		return "\nCommande [numero=" + numero + ", bon_achat=" + bon_achat + ", bon_achat_genere=" + bon_achat_genere
				+ ", adresse=" + adresse + ", client=" + client + ", impressions=" + impressions + ", mode_livraison="
				+ mode_livraison + ", date_commande=" + date_commande + ", statut=" + statut + ", etat_paiement="
				+ etat_paiement + ", montant_total_cmd=" + montant_total_cmd + "]";
	}
}