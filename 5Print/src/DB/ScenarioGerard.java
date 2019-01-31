package DB;

import java.util.ArrayList;

import DTO.Adresse;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.StatutCommande;
import DTO.Stock;
import DTO.TypeSupport;

/*
 * Bernard télécharge une image sur l’application => créer un fichier photos
 * Bernard partage cette image
 * Gérard choisis une nouvelle impression 
 * Puis sélectionne l’image partagée de Bernard
 * Il reçoit un message d’erreur “Vous ne pouvez pas utiliser une image partagée” 
 * Il télécharge puis partage une nouvelle image. 
 * Il re-sélectionne l’image de Bernard 
 * Gérard commande son impression (non validé) 
 * Bernard retire le partage de son image 
 * Gérard reçoit un message d’erreur lors de la confirmation de sa commande
 * Gérard retire l’image anciennement partagée de Bernard et choisit la sienne.
 * Puis Gérard recommande son impression. 
 * Sélectionne son format et valide.
 * */

public class ScenarioGerard implements Runnable {

	private String email;
	private String mdp;
	private Stock stock;
	private Adresse adresse;

	public ScenarioGerard(String email, String mdp, Stock s, Adresse ad) {
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

			int idImp = GestionDB.createImpression(stock.getType_support(), c, stock, 0, 8, false, 1, null, null, null);
			Impression imp = GestionDB.getImpressionById(idImp);
			System.out.println("Impression créée nom=" + this.email);

			// ArrayList<FichierPhoto> lfp = GestionDB.getAllFichierPhotosPartagees(); //
			// POUR "il les recup tous"

			FichierPhoto fp = GestionDB.getFichierPhotoById(false, "/Bernard/Bureau/ChatRose.png");
			System.out.println("FichierPhoto récupéré nom=" + this.email);

			GestionDB.createPhoto(fp.getChemin(), imp, "MagnifiqueChat", 10, 10, 1, 1, "Message:miaou!"); // return
																											// isAdded
			System.out.println("Photo créée nom=" + this.email);

			int idCmd = GestionDB.createCommande(0, email, null, StatutCommande.En_cours, false, 8);
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