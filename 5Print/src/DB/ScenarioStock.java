package DB;

import java.util.ArrayList;

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
	private Stock s;
	private Adresse ad;
	private TypeSupport ts;

	public ScenarioStock(String email, String mdp, Stock s, Adresse ad, TypeSupport ts) {
		this.email = email;
		this.mdp = mdp;
		this.s = s;
		this.ad = ad;
		this.ts = ts;
	}

	@Override
	public void run() {
		if (GestionDB.connectionClient(email, mdp)) {
			System.out.println("Connexion réussi nom=" + this.email);
			Client c = GestionDB.getClientByEmail(email);

			int idImp = GestionDB.createImpression(ts, c, s, 0, 30, false, 20, null, null, null);
			Impression imp = GestionDB.getImpressionById(idImp);
			System.out.println("Impression créée nom=" + this.email);

			int idCmd = GestionDB.createCommande(0, email, null, StatutCommande.En_cours, false, 30);
			Commande cmd = GestionDB.getCommandeById(idCmd);
			System.out.println("Commande créée nom=" + this.email);

			imp.setCommande(cmd);
			GestionDB.updateImpression(ts, imp);
			System.out.println("Update impression nom=" + this.email);

			ArrayList<Impression> limp = new ArrayList<Impression>();
			limp.add(imp);
			cmd.setImpressions(limp);
			cmd.setAdresse(ad);
			GestionDB.updateCommande(cmd);
			System.out.println("Update commande nom=" + this.email);

		} else {
			System.out.println("Connexion non réussi nom=" + this.email);
		}

	}
}