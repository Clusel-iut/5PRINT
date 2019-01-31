package Scenario;

import java.util.ArrayList;

import DB.GestionDB;
import DTO.Adresse;
import DTO.Client;
import DTO.Commande;
import DTO.Impression;
import DTO.StatutCommande;
import DTO.Stock;
import DTO.TypeSupport;


/*
 * François, Françoise et Franck vont en même temps au PhotoNum. Chacun commande
 * une impression tirage. Chacun sélectionne un format A4 normal. Chacun
 * sélectionne 20 feuilles pour son tirage. Chacun confirme en même temps sa
 * commande. François reçoit confirmation de sa commande. Il sélectionne une
 * adresse de livraison et finalise sa commande. Françoise et Franck voient que
 * le stock n’est plus valide. Françoise et Franck rentrent chez eux.
 * 
 * Commentaire sur ce scénario : Le stock étant de 30 feuilles, qu’une seule
 * commande ne devra être imprimée. Le problème est que chacun voit un stock
 * suffisant pour leur impression.
 */
public class ScenarioStock implements Runnable {

	private String email;
	private String mdp;
	private Stock stock;
	private Adresse adresse;

	public ScenarioStock(String email, String mdp, Stock s, Adresse ad) {
		this.email = email;
		this.mdp = mdp;
		this.stock = s;
		this.adresse = ad;
	}

	@Override
	public void run() {
		if (GestionDB.connectionClient(email, mdp)) {
			System.out.println("Connexion réussi nom=" + this.email);
			Client c = GestionDB.getClientByEmail(email);

			// TypeSupport type, Client client, Stock stock, int numero, float montant_total,
			// boolean etat_impression, int nb_impression, String modele, String titre, String mise_en_page)
			int idImp = GestionDB.createImpression(stock.getType_support(), c, stock, 0, 30, false, 20, null, null, null);
			Impression imp = GestionDB.getImpressionById(idImp);
			System.out.println("Impression créée nom=" + this.email);

			// int adresse, String email, String mode_livraison, StatutCommande statut,
			//		boolean etat_paiement, float montant_total_cmd)
			int idCmd = GestionDB.createCommande(0, email, "standard", StatutCommande.En_cours, false, 30);
			Commande cmd = GestionDB.getCommandeById(idCmd);
			System.out.println("Commande créée nom=" + this.email);

			imp.setCommande(cmd);
			GestionDB.updateImpression(stock.getType_support(), imp);
			System.out.println("Update impression nom=" + this.email);

			ArrayList<Impression> limp = new ArrayList<Impression>();
			limp.add(imp);
			cmd.setImpressions(limp);
			cmd.setAdresse(adresse);
			GestionDB.updateCommande(cmd);
			System.out.println("Update commande nom=" + this.email);

		} else {
			System.out.println("Connexion non réussi nom=" + this.email);
		}

	}
}