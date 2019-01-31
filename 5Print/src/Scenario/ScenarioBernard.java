package Scenario;

import java.util.ArrayList;

import DB.GestionDB;
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

public class ScenarioBernard implements Runnable {

	private String email;
	private String mdp;

	public ScenarioBernard(String email, String mdp) {
		this.email = email;
		this.mdp = mdp;
	}

	@Override
	public void run() {
		if (GestionDB.connectionClient(email, mdp)) {
			System.out.println("Connexion réussi nom=" + this.email);
			Client c = GestionDB.getClientByEmail(email);

			GestionDB.createFichierPhoto("/Bernard/Bureau/ChatRose.png", email, "1080*920", "UneImageDeChatEnGrosPlan",
					false); // returne bool isAdded
			System.out.println("FichierPhoto créée nom=" + this.email);

			FichierPhoto fp = GestionDB.getFichierPhotoById(false, "/Bernard/Bureau/ChatRose.png");
			fp.setEst_partage(true);
			GestionDB.updateFichierPhoto(fp);
			System.out.println("FichierPhoto modifié partage vrai nom=" + this.email);

			try {
				System.out.println("Pause 1s  nom=" + this.email);
				Thread.sleep(1000); // 1000 ms -> 1s
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fp.setEst_partage(false);
			GestionDB.updateFichierPhoto(fp);
			System.out.println("FichierPhoto modifié partage faux nom=" + this.email);

		} else {
			System.out.println("Connexion non réussi nom=" + this.email);
		}

	}
}